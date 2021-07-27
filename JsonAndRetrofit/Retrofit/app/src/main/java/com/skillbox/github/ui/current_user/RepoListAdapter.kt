package com.skillbox.github.ui.current_user

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.github.data.UsersRepository

class RepoListAdapter(onItemClick: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<UsersRepository>(RepoDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(RepoListAdapterDelegate(onItemClick))
    }

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