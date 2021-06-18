package com.skillbox.skillbox.myapplication.adapters.images

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.classes.ImagesForLists
import com.skillbox.skillbox.myapplication.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_image_list.*

class ImagesHorizontalAdapterDelegate(
    private val onItemClick: (position: Int) -> Unit
) : AbsListItemAdapterDelegate<ImagesForLists, ImagesForLists, ImagesHorizontalAdapterDelegate.ImageViewHolder>() {

    override fun isForViewType(item: ImagesForLists, items: MutableList<ImagesForLists>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): ImageViewHolder {
        return ImageViewHolder(parent.inflate(R.layout.item_image_list), onItemClick)
    }

    override fun onBindViewHolder(
        item: ImagesForLists,
        holder: ImageViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ImageViewHolder(
        override val containerView: View,
        onItemClick: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            containerView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(image: ImagesForLists) {
            Glide.with(itemView)
                .load(image.picture)
                .error(R.drawable.ic_sync_problem)
                .placeholder(R.drawable.ic_cloud_download)
                .into(imageListImageView)
        }
    }
}