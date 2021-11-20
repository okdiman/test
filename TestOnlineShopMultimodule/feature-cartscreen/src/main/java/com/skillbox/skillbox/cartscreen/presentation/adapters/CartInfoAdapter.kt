package com.skillbox.skillbox.cartscreen.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.cartscreen.domain.entities.Basket

class CartInfoAdapter : AsyncListDifferDelegationAdapter<Basket>(CartInfoDiffUtil()) {

    init {
        delegatesManager.addDelegate(CartInfoAdapterDelegate())
    }

    class CartInfoDiffUtil : DiffUtil.ItemCallback<Basket>() {
        override fun areItemsTheSame(oldItem: Basket, newItem: Basket): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Basket, newItem: Basket): Boolean {
            return oldItem == newItem
        }

    }
}