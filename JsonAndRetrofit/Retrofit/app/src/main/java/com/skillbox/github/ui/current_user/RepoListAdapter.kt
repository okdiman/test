package com.skillbox.github.ui.current_user

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.github.data.UsersRepository

class RepoListAdapter :
    AsyncListDifferDelegationAdapter<UsersRepository>(RepoDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(RepoListAdapterDelegate())
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