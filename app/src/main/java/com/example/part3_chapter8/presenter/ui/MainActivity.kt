package com.example.part3_chapter8.presenter.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.part3_chapter8.R
import com.example.part3_chapter8.databinding.ActivityMainBinding
import com.example.part3_chapter8.domain.model.Content
import com.example.part3_chapter8.presenter.ui.list.ListAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
    }

    fun onClickAdd() {

    }

    inner class Handler {
        fun onClickItem(item: Content) {

        }

        fun onLongClickItem(item: Content): Boolean {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Really delete?")
                .setPositiveButton("Yes") { _, _ ->

                }.setNegativeButton("No") { _, _ ->

                }.show()

            return false
        }
    }
}