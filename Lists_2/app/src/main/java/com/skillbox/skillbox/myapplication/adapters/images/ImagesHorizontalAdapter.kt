package com.skillbox.skillbox.myapplication.adapters.images

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.myapplication.classes.Images

class ImagesHorizontalAdapter(onItemClick: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<Images>(ImagesDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ImagesHorizontalAdapterDelegate(onItemClick))
    }

    class ImagesDiffUtilCallback : DiffUtil.ItemCallback<Images>() {
        override fun areItemsTheSame(oldItem: Images, newItem: Images): Boolean {
            return when (newItem.id) {
                oldItem.id -> true
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Images, newItem: Images): Boolean {
            return oldItem == newItem
        }
    }
}