package com.skillbox.skillbox.location.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.location.R
import com.skillbox.skillbox.location.classes.PointOfLocation
import com.skillbox.skillbox.location.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_location.*

class LocationsListAdapterDelegate(private val onItemClick: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<PointOfLocation, PointOfLocation, LocationsListAdapterDelegate.LocationsViewHolder>() {

    override fun isForViewType(
        item: PointOfLocation,
        items: MutableList<PointOfLocation>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): LocationsViewHolder {
        return LocationsViewHolder(parent.inflate(R.layout.item_location), onItemClick)
    }

    override fun onBindViewHolder(
        item: PointOfLocation,
        holder: LocationsViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class LocationsViewHolder(
        override val containerView: View,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        private var formatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")
            .withZone(org.threeten.bp.ZoneId.systemDefault())

        @SuppressLint("SetTextI18n")
        fun bind(location: PointOfLocation) {
            locationTextView.text =
                "Lat = ${location.lat}, Lng = ${location.lng}, Alt = ${location.alt},Speed = ${location.speed},Accuracy = ${location.accuracy}"
            Glide.with(itemView)
                .load(location.picture)
                .placeholder(R.drawable.ic_cloud_download)
                .error(R.drawable.autocheckpoint)
                .into(locationImageView)
            dateOfLocationTextView.text = formatter.format(location.pointOfTime)
        }
    }
}