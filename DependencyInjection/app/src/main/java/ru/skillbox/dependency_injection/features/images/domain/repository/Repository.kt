package ru.skillbox.dependency_injection.features.images.domain.repository

import android.net.Uri
import ru.skillbox.dependency_injection.features.images.domain.entities.Image

interface Repository {
    fun observeImages(onChange: () -> Unit)
    fun unregisterObserver()
    suspend fun getImages(): List<Image>
    suspend fun saveImage(name: String, url: String)
    fun saveImageDetails(name: String): Uri
    fun makeImageVisible(imageUri: Uri)
    suspend fun downloadImage(url: String, uri: Uri)
    suspend fun deleteImage(id: Long)
}