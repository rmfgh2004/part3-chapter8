package com.example.part3_chapter8.presenter.ui.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.part3_chapter8.databinding.ItemContentBinding
import com.example.part3_chapter8.domain.model.Content
import com.example.part3_chapter8.presenter.ui.MainActivity

class ContentViewHolder(
    private val binding: ItemContentBinding,
    private val handler: MainActivity.Handler
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Content) {
        binding.item = item
        binding.handler = handler
    }
}