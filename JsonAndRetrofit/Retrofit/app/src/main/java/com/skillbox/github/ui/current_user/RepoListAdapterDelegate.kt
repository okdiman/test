package com.skillbox.github.ui.current_user

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.github.R
import com.skillbox.github.data.UsersRepository
import com.skillbox.github.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_for_repository_list.*

class RepoListAdapterDelegate :
    AbsListItemAdapterDelegate<UsersRepository, UsersRepository, RepoListAdapterDelegate.Holder>() {

    class Holder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(item: UsersRepository) {
            itemTextView.text = item.name
            itemIdTextView.text = item.id.toString()
        }
    }

    override fun isForViewType(
        item: UsersRepository,
        items: MutableList<UsersRepository>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_for_repository_list))
    }

    override fun onBindViewHolder(
        item: UsersRepository,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}