package com.skillbox.skillbox.myapplication.adapters

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.classes.Resorts
import com.skillbox.skillbox.myapplication.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_mountain.*

class MountainAdapterDelegate(
    private val onItemClick: (position: Int) -> Unit
) :
    AbsListItemAdapterDelegate<Resorts.Mountain, Resorts, MountainAdapterDelegate.MountainHolder>() {

//    указываем, что обрабатываем только тип Mountain
    override fun isForViewType(item: Resorts, items: MutableList<Resorts>, position: Int): Boolean {
        return item is Resorts.Mountain
    }

    override fun onCreateViewHolder(parent: ViewGroup): MountainHolder {
        return MountainHolder(parent.inflate(R.layout.item_mountain), onItemClick)
    }

    override fun onBindViewHolder(
        item: Resorts.Mountain,
        holder: MountainHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class MountainHolder(
        containerView: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseViewHolder(containerView, onItemClick), LayoutContainer {
        fun bind(mountain: Resorts.Mountain) {
            mountainTextView.text = mountain.mountain
            baseBindInfo(mountain.name, mountain.country, mountain.photo)
        }
    }
}