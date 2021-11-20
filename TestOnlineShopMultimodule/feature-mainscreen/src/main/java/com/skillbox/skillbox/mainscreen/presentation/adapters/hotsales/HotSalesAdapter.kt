package com.skillbox.skillbox.mainscreen.presentation.adapters.hotsales

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.mainscreen.domain.entities.HotSales

class HotSalesAdapter(onBuyButtonClick: () -> Unit) :
    AsyncListDifferDelegationAdapter<HotSales>(HotSellersDiffUtil()) {

    init {
        delegatesManager.addDelegate(HotSalesAdapterDelegate(onBuyButtonClick))
    }

    //    создаем DiffUtil для нашего адаптера
    class HotSellersDiffUtil : DiffUtil.ItemCallback<HotSales>() {
        override fun areItemsTheSame(oldItem: HotSales, newItem: HotSales): Boolean {
//            сравниваем продукты по id на первичном этапе
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HotSales, newItem: HotSales): Boolean {
//            сравниваем остальные поля, если id совпадают
            return oldItem == newItem
        }
    }
}