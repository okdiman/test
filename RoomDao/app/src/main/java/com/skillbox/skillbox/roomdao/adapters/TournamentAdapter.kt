package com.skillbox.skillbox.roomdao.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.roomdao.database.entities.Tournaments

class TournamentAdapter(onTournamentClick: (Tournaments) -> Unit) :
    AsyncListDifferDelegationAdapter<Tournaments>(TournamentDiffUtil()) {

    init {
        delegatesManager.addDelegate(TournamentAdapterDelegate(onTournamentClick))
    }

    class TournamentDiffUtil() : DiffUtil.ItemCallback<Tournaments>() {
        override fun areItemsTheSame(oldItem: Tournaments, newItem: Tournaments): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tournaments, newItem: Tournaments): Boolean {
            return oldItem == newItem
        }

    }
}