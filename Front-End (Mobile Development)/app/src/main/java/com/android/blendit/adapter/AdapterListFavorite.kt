package com.android.blendit.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.blendit.R
import com.android.blendit.databinding.FavoriteItemBinding
import com.android.blendit.preference.AccountPreference
import com.android.blendit.remote.response.ItemsFavorite
import com.android.blendit.ui.activity.detail.DetailActivity
import com.android.blendit.viewmodel.Repository
import com.bumptech.glide.Glide

class AdapterListFavorite : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<ItemsFavorite>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listFavorite: List<ItemsFavorite>) {
        list.clear()
        list.addAll(listFavorite)
        notifyDataSetChanged()
    }

    inner class NormalViewHolder(val binding: FavoriteItemBinding) : ViewHolder(binding.root) {

        private val accountPreference = AccountPreference(itemView.context)
        private val repository = Repository(accountPreference)

        override fun bindView(itemsFavorite: ItemsFavorite) {
            binding.productName.text = itemsFavorite.productName
            binding.brand.text = itemsFavorite.brand
            Glide.with(binding.root).load(itemsFavorite.picture).into(binding.imageView)
            binding.btnFavorite.isChecked = true

            binding.btnFavorite.addOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    repository.addFavorite(itemsFavorite.productId)
                } else {
                    repository.removeFavorite(itemsFavorite.productId)
                }
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.ID, itemsFavorite.productId)
                    putExtra(DetailActivity.BRAND, itemsFavorite.brand)
                    putExtra(DetailActivity.PRODUCT_NAME, itemsFavorite.productName)
                    putExtra(DetailActivity.PICTURE, itemsFavorite.picture)
                    putExtra(DetailActivity.IS_FAVORITE, binding.btnFavorite.isChecked)
                }
                it.context.startActivity(intent)
            }
        }
    }

    inner class FooterViewHolder(itemView: View) : ViewHolder(itemView) {
        override fun bindView(itemsFavorite: ItemsFavorite) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == FOOTER_VIEW) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.footer_layout, parent, false)
            FooterViewHolder(view)
        } else {
            NormalViewHolder(
                FavoriteItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NormalViewHolder) {
            val data = list[position]
            holder.bindView(data)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size) {
            FOOTER_VIEW
        } else {
            super.getItemViewType(position)
        }
    }

    override fun getItemCount(): Int = list.size + 1

    abstract inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindView(itemsFavorite: ItemsFavorite)
    }

    companion object {
        const val FOOTER_VIEW = 1
    }
}