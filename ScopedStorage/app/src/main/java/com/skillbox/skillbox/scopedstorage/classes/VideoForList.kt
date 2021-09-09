package com.skillbox.skillbox.scopedstorage.classes

import android.net.Uri

data class VideoForList(
    val id: Long,
    val uri: Uri,
    val title: String,
    val size: Int
)