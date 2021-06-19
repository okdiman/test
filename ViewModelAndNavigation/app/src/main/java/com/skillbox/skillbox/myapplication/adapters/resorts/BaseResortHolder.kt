package com.skillbox.skillbox.myapplication.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.skillbox.myapplication.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_mountain.*

abstract class BaseViewHolder(
    final override val containerView: View,
    onItemClick: (id: Long) -> Unit,
    onLongItemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private var currentId: Long? = null

    //  обработка клика по элементу
    init {
        containerView.setOnClickListener {
            currentId?.let {
                onItemClick(it)
            }
        }
        containerView.setOnLongClickListener {
            onLongItemClick(adapterPosition)
            return@setOnLongClickListener true
        }
    }
    //  общий набор данных для обоих View Holder
    protected fun baseBindInfo(
        id: Long,
        name: String?,
        country: String?,
        photo: String?
    ) {
        currentId = id
        Glide.with(itemView)
            .load(photo)
            .error(R.drawable.ic_sync_problem)
            .placeholder(R.drawable.ic_cloud_download)
            .into(photoImageView)
        nameTextView.text = name
        countryTextView.text = country
    }
}