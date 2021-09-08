package com.skillbox.skillbox.scopedstorage.adapters

import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.scopedstorage.R
import com.skillbox.skillbox.scopedstorage.classes.VideoForList
import com.skillbox.skillbox.scopedstorage.utils.glideLoadImage
import com.skillbox.skillbox.scopedstorage.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.video_item.*

class VideoAdapterDelegate :
    AbsListItemAdapterDelegate<VideoForList, VideoForList, VideoAdapterDelegate.Holder>() {

    class Holder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(video: VideoForList) {
            videoImageView.glideLoadImage(video.preview.toUri())
            titleOfVideoTextView.text = video.title
            sizeOfVideoTextView.text = video.size.toString()
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
        return Holder(parent.inflate(R.layout.video_item))
    }

    override fun onBindViewHolder(item: VideoForList, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
}