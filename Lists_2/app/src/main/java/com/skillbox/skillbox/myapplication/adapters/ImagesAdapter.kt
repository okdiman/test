package com.skillbox.skillbox.myapplication.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.classes.Images
import com.skillbox.skillbox.myapplication.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_image_list.*

class ImagesAdapter (private val onItemClick: (position: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var images = emptyList<Images>()

    // количество элементов равно количеству элементов в списке
    override fun getItemCount(): Int {
        return images.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageViewHolder(parent.inflate(R.layout.item_image_list), onItemClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> {
                val image = images [position]
                holder.bind(image)
            }
        }
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

        fun bind(image: Images) {
            Glide.with(itemView)
                .load(image.picture)
                .error(R.drawable.ic_sync_problem)
                .placeholder(R.drawable.ic_cloud_download)
                .into(imageListImageView)
        }
    }

    fun updateImages(newImages: List<Images>) {
        images = newImages
    }
}