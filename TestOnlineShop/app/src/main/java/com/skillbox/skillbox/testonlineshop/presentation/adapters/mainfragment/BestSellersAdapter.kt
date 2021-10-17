package com.skillbox.skillbox.testonlineshop.presentation.adapters.mainfragment

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.testonlineshop.domain.models.Product

class BestSellersAdapter(onItemClick: (item: Product) -> Unit) :
    AsyncListDifferDelegationAdapter<Product>(BestSellersDiffUtil()) {

    init {
        delegatesManager.addDelegate(BestSellersAdapterDelegate(onItemClick))
    }

    //    создаем DiffUtil для нашего адаптера
    class BestSellersDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
//            сравниваем продукты по id на первичном этапе
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
//            сравниваем остальные поля, если id совпадают
            return oldItem == newItem
        }
    }
}