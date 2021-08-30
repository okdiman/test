package com.skillbox.skillbox.roomdao.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.roomdao.database.entities.Clubs

class ClubAdapter(onClubClick: (Clubs) -> Unit) :
    AsyncListDifferDelegationAdapter<Clubs>(ClubDiffUtil()) {

    //    инициализируем делегата
    init {
        delegatesManager.addDelegate(ClubAdapterDelegate(onClubClick))
    }

    //    создаем DiffUtil для нашего адаптера
    class ClubDiffUtil : DiffUtil.ItemCallback<Clubs>() {
        override fun areItemsTheSame(oldItem: Clubs, newItem: Clubs): Boolean {
//            сравниваем клубы по названию на первичном этапе
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Clubs, newItem: Clubs): Boolean {
//            сравниваем остальные поля, если название совпадает
            return oldItem == newItem
        }

    }
}