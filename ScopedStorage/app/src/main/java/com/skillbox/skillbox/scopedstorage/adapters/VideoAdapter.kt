package com.skillbox.skillbox.scopedstorage.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.scopedstorage.classes.VideoForList

class VideoAdapter(onDeleteVideo: (id: Long) -> Unit) :
    AsyncListDifferDelegationAdapter<VideoForList>(VideoDiffUtil()) {
    //    инициализируем делегат для нашего адаптера
    init {
        delegatesManager.addDelegate(VideoAdapterDelegate(onDeleteVideo))
    }

    //    создаем DiffUtil для сравнения элементов списка в адаптере
    class VideoDiffUtil : DiffUtil.ItemCallback<VideoForList>() {
        override fun areItemsTheSame(oldItem: VideoForList, newItem: VideoForList): Boolean {
//            первично сравниваем видео по id
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: VideoForList, newItem: VideoForList): Boolean {
            return oldItem == newItem
        }
    }
}