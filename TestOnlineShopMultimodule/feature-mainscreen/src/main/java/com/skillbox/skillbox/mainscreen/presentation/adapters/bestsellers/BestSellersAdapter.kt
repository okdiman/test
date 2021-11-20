package com.skillbox.skillbox.mainscreen.presentation.adapters.bestsellers

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.mainscreen.domain.entities.BestSellers

class BestSellersAdapter(onItemClick: (item: BestSellers) -> Unit) :
    AsyncListDifferDelegationAdapter<BestSellers>(BestSellersDiffUtil()) {

    init {
        delegatesManager.addDelegate(BestSellersAdapterDelegate(onItemClick))
    }

    //    создаем DiffUtil для нашего адаптера
    class BestSellersDiffUtil : DiffUtil.ItemCallback<BestSellers>() {
        override fun areItemsTheSame(oldItem: BestSellers, newItem: BestSellers): Boolean {
//            сравниваем продукты по id на первичном этапе
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BestSellers, newItem: BestSellers): Boolean {
//            сравниваем остальные поля, если id совпадают
            return oldItem == newItem
        }
    }
}