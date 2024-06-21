package com.android.blendit.ui.activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.blendit.R
import com.android.blendit.databinding.CategoryItemBinding
import com.android.blendit.remote.response.ListCategory

import com.bumptech.glide.Glide
class CategoryAdapter(
    private val categories: List<ListCategory>,
    private val onItemClick: (ListCategory) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: ListCategory) {
            binding.tvCategory.text = category.category
            Glide.with(binding.root.context)
                .load(category.thumbnail)
                .into(binding.ivCategory)

            binding.root.setOnClickListener {
                onItemClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}