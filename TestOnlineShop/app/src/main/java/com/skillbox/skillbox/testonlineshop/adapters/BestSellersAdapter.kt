package com.skillbox.skillbox.testonlineshop.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.testonlineshop.classes.BestSellers

class BestSellersAdapter : AsyncListDifferDelegationAdapter<BestSellers>(BestSellersDiffUtil()) {

    init {
        delegatesManager.addDelegate(BestSellersAdapterDelegate())
    }

    //    создаем DiffUtil для нашего адаптера
    class BestSellersDiffUtil : DiffUtil.ItemCallback<BestSellers>() {
        override fun areItemsTheSame(oldItem: BestSellers, newItem: BestSellers): Boolean {
//            сравниваем фильмы по id на первичном этапе
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BestSellers, newItem: BestSellers): Boolean {
//            сравниваем остальные поля, если id совпадают
            return oldItem == newItem
        }
    }
}