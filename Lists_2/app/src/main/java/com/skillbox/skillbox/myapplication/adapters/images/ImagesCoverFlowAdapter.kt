package com.skillbox.skillbox.myapplication.adapters.images

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.skillbox.myapplication.classes.ImagesForLists

class ImagesCoverFlowAdapter (onItemClick: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<ImagesForLists>(ImagesHorizontalAdapter.ImagesDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ImagesCoverFlowDelegate(onItemClick))
    }
}