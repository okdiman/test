package com.skillbox.skillbox.myapplication.adapters.resorts

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.adapters.BaseViewHolder
import com.skillbox.skillbox.myapplication.classes.Resorts
import com.skillbox.skillbox.myapplication.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_sea.*

class SeaAdapterDelegate(
    private val onItemClick: (id: Long) -> Unit,
    private val onLongItemClick: (position: Int) -> Unit
) :
    AbsListItemAdapterDelegate<Resorts.Sea, Resorts, SeaAdapterDelegate.SeaHolder>() {

    //    указываем, что обрабатываем только тип Sea
    override fun isForViewType(item: Resorts, items: MutableList<Resorts>, position: Int): Boolean {
        return item is Resorts.Sea
    }

    override fun onCreateViewHolder(parent: ViewGroup): SeaHolder {
        return SeaHolder(parent.inflate(R.layout.item_sea), onItemClick, onLongItemClick)
    }

    override fun onBindViewHolder(
        item: Resorts.Sea,
        holder: SeaHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    //  создаем класс холдера
    class SeaHolder(
        containerView: View,
        onItemClick: (id: Long) -> Unit,
        onLongItemClick: (position: Int) -> Unit
    ) : BaseViewHolder(containerView, onItemClick, onLongItemClick), LayoutContainer {
        fun bind(sea: Resorts.Sea) {
            seaTextView.text = sea.sea
            baseBindInfo(sea.id, sea.name, sea.country, sea.photo)
        }
    }
}