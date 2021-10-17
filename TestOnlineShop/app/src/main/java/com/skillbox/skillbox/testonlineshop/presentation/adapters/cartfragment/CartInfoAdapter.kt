package com.skillbox.skillbox.testonlineshop.presentation.adapters.cartfragment

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.testonlineshop.domain.models.Basket

class CartInfoAdapter : AsyncListDifferDelegationAdapter<Basket>(CartInfoDiffUtil()) {

    init {
        delegatesManager.addDelegate(CartInfoAdapterDelegate())
    }

    class CartInfoDiffUtil() : DiffUtil.ItemCallback<Basket>() {
        override fun areItemsTheSame(oldItem: Basket, newItem: Basket): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Basket, newItem: Basket): Boolean {
            return oldItem == newItem
        }

    }
}