package com.skillbox.skillbox.testonlineshop.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class DetailsInfoAdapter :
    AsyncListDifferDelegationAdapter<String>(DetailsInfoDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(DetailsInfoAdapterDelegate())
    }

    class DetailsInfoDiffUtilCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}