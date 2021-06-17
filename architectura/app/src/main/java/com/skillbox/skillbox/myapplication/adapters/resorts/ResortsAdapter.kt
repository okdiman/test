package com.skillbox.skillbox.myapplication.adapters.resorts

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.myapplication.classes.Resorts


//  наследуемся от AsyncListDifferDelegationAdapter так как он внутри содержит все необходимые методы
class ResortsAdapter(
    onItemClick: (position: Int) -> Unit
) : AsyncListDifferDelegationAdapter<Resorts>(ResortDiffUtilCallback()) {

    //    создаем делегат менеджер и указываем обрабатываемые делегаты
    init {
        delegatesManager.addDelegate(SeaAdapterDelegate(onItemClick))
            .addDelegate(OceanAdapterDelegate(onItemClick))
            .addDelegate(MountainAdapterDelegate(onItemClick))
    }
    //  обрабатываем метод сравнения элементов
    class ResortDiffUtilCallback : DiffUtil.ItemCallback<Resorts>() {
        override fun areItemsTheSame(oldItem: Resorts, newItem: Resorts): Boolean {
            return when {
                oldItem is Resorts.Sea && newItem is Resorts.Sea -> newItem.id == oldItem.id
                oldItem is Resorts.Ocean && newItem is Resorts.Ocean -> newItem.id == oldItem.id
                oldItem is Resorts.Mountain && newItem is Resorts.Mountain -> newItem.id == oldItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Resorts, newItem: Resorts): Boolean {
            return oldItem == newItem
        }

    }
}