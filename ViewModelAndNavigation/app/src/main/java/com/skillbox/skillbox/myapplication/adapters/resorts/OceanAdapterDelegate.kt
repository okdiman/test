package com.skillbox.skillbox.myapplication.adapters.resorts

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.adapters.BaseViewHolder
import com.skillbox.skillbox.myapplication.classes.Resorts
import com.skillbox.skillbox.myapplication.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_oceans.*

class OceanAdapterDelegate(
    private val onItemClick: (id: Long) -> Unit,
    private val onLongItemClick: (position: Int) -> Unit
) :
    AbsListItemAdapterDelegate<Resorts.Ocean, Resorts, OceanAdapterDelegate.OceanHolder>() {

    //   указываем, что обрабатываем только тип Ocean
    override fun isForViewType(item: Resorts, items: MutableList<Resorts>, position: Int): Boolean {
        return item is Resorts.Ocean
    }

    override fun onCreateViewHolder(parent: ViewGroup): OceanHolder {
        return OceanHolder(parent.inflate(R.layout.item_oceans), onItemClick, onLongItemClick)
    }

    override fun onBindViewHolder(
        item: Resorts.Ocean,
        holder: OceanHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    //  создаем класс холдера
    class OceanHolder(
        containerView: View,
        onItemClick: (id: Long) -> Unit,
        onLongItemClick: (position: Int) -> Unit
    ) : BaseViewHolder(containerView, onItemClick, onLongItemClick), LayoutContainer {
        fun bind(ocean: Resorts.Ocean) {
            oceanTextView.text = ocean.ocean
            baseBindInfo(ocean.id, ocean.name, ocean.country, ocean.photo)
        }
    }
}