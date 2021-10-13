package com.skillbox.skillbox.testonlineshop.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.testonlineshop.classes.HotSales

class HotSalesAdapter : AsyncListDifferDelegationAdapter<HotSales>(HotSellersDiffUtil()) {

    init {
        delegatesManager.addDelegate(HotSalesAdapterDelegate())
    }

    //    создаем DiffUtil для нашего адаптера
    class HotSellersDiffUtil : DiffUtil.ItemCallback<HotSales>() {
        override fun areItemsTheSame(oldItem: HotSales, newItem: HotSales): Boolean {
//            сравниваем фильмы по id на первичном этапе
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HotSales, newItem: HotSales): Boolean {
//            сравниваем остальные поля, если id совпадают
            return oldItem == newItem
        }
    }
}