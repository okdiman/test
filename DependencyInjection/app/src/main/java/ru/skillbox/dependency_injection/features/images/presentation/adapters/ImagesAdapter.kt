package ru.skillbox.dependency_injection.features.images.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.dependency_injection.features.images.domain.entities.Image

class ImagesAdapter(
    onDeleteImage: (id: Long) -> Unit
): AsyncListDifferDelegationAdapter<Image>(ImageDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ImageAdapterDelegate(onDeleteImage))
    }

    class ImageDiffUtilCallback: DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }

}