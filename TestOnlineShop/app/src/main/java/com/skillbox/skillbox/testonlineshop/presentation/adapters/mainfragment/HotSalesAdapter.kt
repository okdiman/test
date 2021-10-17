package com.skillbox.skillbox.testonlineshop.presentation.adapters.mainfragment

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.testonlineshop.domain.models.Product

class HotSalesAdapter(onBuyButtonClick: () -> Unit) :
    AsyncListDifferDelegationAdapter<Product>(HotSellersDiffUtil()) {

    init {
        delegatesManager.addDelegate(HotSalesAdapterDelegate(onBuyButtonClick))
    }

    //    создаем DiffUtil для нашего адаптера
    class HotSellersDiffUtil : DiffUtil.ItemCallback<Product>() {
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