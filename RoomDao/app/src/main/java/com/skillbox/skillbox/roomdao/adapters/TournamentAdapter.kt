package com.skillbox.skillbox.roomdao.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.roomdao.database.entities.Tournaments

class TournamentAdapter(onTournamentClick: (Tournaments) -> Unit) :
    AsyncListDifferDelegationAdapter<Tournaments>(TournamentDiffUtil()) {

    //    инициализируем делегата
    init {
        delegatesManager.addDelegate(TournamentAdapterDelegate(onTournamentClick))
    }

    //    создаем DiffUtil для нашего адаптера
    class TournamentDiffUtil : DiffUtil.ItemCallback<Tournaments>() {
        override fun areItemsTheSame(oldItem: Tournaments, newItem: Tournaments): Boolean {
//            сравниваем турниры по id на первичном этапе
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tournaments, newItem: Tournaments): Boolean {
//            сравниваем остальные поля, если название совпадает
            return oldItem == newItem
        }

    }
}