package com.skillbox.skillbox.roomdao.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.roomdao.database.entities.Clubs

class ClubAdapter(onClubClick: (Clubs) -> Unit) :
    AsyncListDifferDelegationAdapter<Clubs>(ClubDiffUtil()) {
    init {
        delegatesManager.addDelegate(ClubAdapterDelegate(onClubClick))
    }

    class ClubDiffUtil() : DiffUtil.ItemCallback<Clubs>() {
        override fun areItemsTheSame(oldItem: Clubs, newItem: Clubs): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Clubs, newItem: Clubs): Boolean {
            return oldItem == newItem
        }

    }
}