package com.skillbox.skillbox.myapplication.adapters.images

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.myapplication.classes.ImagesForLists

class ImagesHorizontalAdapter(onItemClick: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<ImagesForLists>(ImagesDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ImagesHorizontalAdapterDelegate(onItemClick))
    }

    class ImagesDiffUtilCallback : DiffUtil.ItemCallback<ImagesForLists>() {
        override fun areItemsTheSame(oldItem: ImagesForLists, newItem: ImagesForLists): Boolean {
            return when (newItem.id) {
                oldItem.id -> true
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ImagesForLists, newItem: ImagesForLists): Boolean {
            return oldItem == newItem
        }
    }
}