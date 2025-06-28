package com.example.part3_chapter8.presenter.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.part3_chapter8.R
import com.example.part3_chapter8.databinding.ActivityInputBinding
import com.example.part3_chapter8.domain.model.Content
import com.example.part3_chapter8.presenter.viewmodel.InputViewModel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding
    private val viewModel: InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInputBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@InputActivity
            viewModel = this@InputActivity.viewModel
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (intent.getSerializableExtra(ITEM) as Content?)?.let {
            viewModel.initData(it)
        }

        observeViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun observeViewModel() {
        viewModel.doneEvent.observe(this) {
            Toast.makeText(this, it.second, Toast.LENGTH_SHORT).show()
            if (it.first) {
                finish()
            }
        }
    }

    companion object {
        private const val ITEM = "item"
        fun start(context: Context, item: Content? = null) {
            Intent(context, InputActivity::class.java).apply {
                putExtra(ITEM, item)
            }.run {
                context.startActivity(this)
            }
        }
    }

}