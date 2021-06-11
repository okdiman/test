package com.skillbox.skillbox.myapplication.adapters.images

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.myapplication.classes.Images

class ImagesGridAdapter(onItemClick: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<Images>(ImagesHorizontalAdapter.ImagesDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ImagesGridAdapterDelegate(onItemClick))
    }
}