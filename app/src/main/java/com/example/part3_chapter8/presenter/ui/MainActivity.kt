package com.example.part3_chapter8.presenter.ui

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.part3_chapter8.R
import com.example.part3_chapter8.databinding.ActivityMainBinding
import com.example.part3_chapter8.domain.model.Content
import com.example.part3_chapter8.presenter.ui.list.ListAdapter
import com.example.part3_chapter8.presenter.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val adapter by lazy { ListAdapter(Handler()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            view = this@MainActivity
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL)
            )
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeViewModel()
    }

    fun onClickAdd() {
        InputActivity.start(this)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.contentList
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest {
                    binding.apply {
                        progressBar.isVisible = false
                        emptyTextView.isVisible = it.isEmpty()
                        recyclerView.isVisible = it.isNotEmpty()
                    }
                    adapter.submitList(it)
                }
        }

        viewModel.doneEvent.observe(this) {
            if (it.first) {
                Toast.makeText(this@MainActivity, it.second, Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class Handler {
        fun onClickItem(item: Content) {
            InputActivity.start(this@MainActivity, item)
        }

        fun onLongClickItem(item: Content): Boolean {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Really delete?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteItem(item)
                }.setNegativeButton("No") { _, _ ->

                }.show()

            return false
        }
    }
}