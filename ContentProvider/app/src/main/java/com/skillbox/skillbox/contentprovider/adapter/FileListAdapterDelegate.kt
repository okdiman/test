package com.skillbox.skillbox.contentprovider.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.skillbox.contentprovider.R
import com.skillbox.skillbox.contentprovider.classes.FileForList
import com.skillbox.skillbox.contentprovider.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_of_files_item.*


class FileListAdapterDelegate(private val onContactClick: (FileForList) -> Unit) :
    AbsListItemAdapterDelegate<FileForList, FileForList, FileListAdapterDelegate.Holder>() {
//    создаем ViewHolder для работы с нашими элементами списка (заполнение и клик лисенер)
    class Holder(
        override val containerView: View,
        onContactClick: (FileForList) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private var currentFileForList: FileForList? = null
//    инициализруем клик лисенер
        init {
            containerView.setOnClickListener { currentFileForList?.let(onContactClick) }
        }
//    баиндим данные в элемент списка
        fun bind(fileForList: FileForList) {
            currentFileForList = fileForList
            item_of_files_list.text = fileForList.name
        }
    }

//    имплементируем методы AbsListItemAdapterDelegate
    override fun isForViewType(item: FileForList, items: MutableList<FileForList>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.list_of_files_item), onContactClick)
    }

    override fun onBindViewHolder(
        item: FileForList,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}