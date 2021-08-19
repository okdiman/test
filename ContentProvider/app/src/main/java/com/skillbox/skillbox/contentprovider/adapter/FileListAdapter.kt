package com.skillbox.skillbox.contentprovider.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.contentprovider.classes.FileForList

class FileListAdapter (onContactClick: (FileForList) -> Unit) :
    AsyncListDifferDelegationAdapter<FileForList>(FileDiffUtilCallback()) {
//    создаем делегат для нашего адаптера
    init {
        delegatesManager.addDelegate(FileListAdapterDelegate(onContactClick))
    }
//    создаем класс DiffUtilCallback для нашего адаптера
    class FileDiffUtilCallback : DiffUtil.ItemCallback<FileForList>() {
        override fun areItemsTheSame(oldItem: FileForList, newItem: FileForList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FileForList, newItem: FileForList): Boolean {
            return oldItem == newItem
        }
    }
}