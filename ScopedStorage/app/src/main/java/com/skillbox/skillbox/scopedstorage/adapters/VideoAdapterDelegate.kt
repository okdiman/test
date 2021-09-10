package com.skillbox.skillbox.scopedstorage.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.scopedstorage.classes.VideoForList
import com.skillbox.skillbox.scopedstorage.databinding.VideoItemBinding
import com.skillbox.skillbox.scopedstorage.utils.glideLoadImage
import com.skillbox.skillbox.scopedstorage.utils.inflate

class VideoAdapterDelegate(private val onDeleteVideo: (id: Long) -> Unit) :
    AbsListItemAdapterDelegate<VideoForList, VideoForList, VideoAdapterDelegate.Holder>() {

    class Holder(
        private val binding: VideoItemBinding,
        onDeleteVideo: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentVideoId: Long? = null

        init {
            binding.deleteActionButton.setOnClickListener {
                currentVideoId?.let(onDeleteVideo)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(video: VideoForList) {
            currentVideoId = video.id
            binding.videoImageView.glideLoadImage(video.uri)
            binding.titleOfVideoTextView.text = "Title: ${video.title}"
            binding.sizeOfVideoTextView.text = "Size: ${video.size / 1024} kb"
        }
    }

    override fun isForViewType(
        item: VideoForList,
        items: MutableList<VideoForList>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(VideoItemBinding::inflate), onDeleteVideo)
    }

    override fun onBindViewHolder(item: VideoForList, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
}