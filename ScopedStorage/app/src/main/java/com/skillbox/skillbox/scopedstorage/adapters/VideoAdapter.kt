package com.skillbox.skillbox.scopedstorage.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.scopedstorage.classes.VideoForList

class VideoAdapter : AsyncListDifferDelegationAdapter<VideoForList>(VideoDiffUtil()) {
    init {
        delegatesManager.addDelegate(VideoAdapterDelegate())
    }

    class VideoDiffUtil : DiffUtil.ItemCallback<VideoForList>() {
        override fun areItemsTheSame(oldItem: VideoForList, newItem: VideoForList): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: VideoForList, newItem: VideoForList): Boolean {
            return oldItem == newItem
        }
    }
}