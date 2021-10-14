package com.skillbox.skillbox.testonlineshop.adapters

import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.testonlineshop.databinding.ItemDetailsInfoRecyclerViewBinding
import com.skillbox.skillbox.testonlineshop.utils.glideLoadImage
import com.skillbox.skillbox.testonlineshop.utils.inflate

class DetailsInfoAdapterDelegate :
    AbsListItemAdapterDelegate<String, String, DetailsInfoAdapterDelegate.Holder>() {
    //    создаем холдер для recycler view
    class Holder(private val binding: ItemDetailsInfoRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.run {
                itemDetailsInfoImageView.glideLoadImage(item.toUri())
            }
        }
    }

    override fun isForViewType(item: String, items: MutableList<String>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(ItemDetailsInfoRecyclerViewBinding::inflate))
    }

    override fun onBindViewHolder(item: String, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
}