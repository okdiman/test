package ru.skillbox.dependency_injection.features.images.domain.entities

import android.net.Uri

data class Image(
    val id: Long,
    val uri: Uri,
    val name: String,
    val size: Int
)