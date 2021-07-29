package com.skillbox.github.ui.repository_list

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.github.data.UsersRepository

class RepoListAdapter(onItemClick: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<UsersRepository>(RepoDiffUtilCallback()) {

//обработка клика по репозиторию
    init {
        delegatesManager.addDelegate(RepoListAdapterDelegate(onItemClick))
    }

//diff util для списка репозиториев
    class RepoDiffUtilCallback : DiffUtil.ItemCallback<UsersRepository>() {
        override fun areItemsTheSame(oldItem: UsersRepository, newItem: UsersRepository): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UsersRepository,
            newItem: UsersRepository
        ): Boolean {
            return oldItem == newItem
        }
    }
}