package com.skillbox.skillbox.location.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.location.classes.PointOfLocation

class LocationsListAdapter(onItemClick: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<PointOfLocation>(LocationsDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(LocationsListAdapterDelegate(onItemClick))
    }

    class LocationsDiffUtilCallback : DiffUtil.ItemCallback<PointOfLocation>() {
        override fun areItemsTheSame(oldItem: PointOfLocation, newItem: PointOfLocation): Boolean {
            return when (newItem.id) {
                oldItem.id -> true
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: PointOfLocation,
            newItem: PointOfLocation
        ): Boolean {
            return oldItem == newItem
        }
    }
}