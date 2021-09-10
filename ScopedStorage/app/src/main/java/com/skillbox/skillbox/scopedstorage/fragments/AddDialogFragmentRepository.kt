package com.skillbox.skillbox.scopedstorage.fragments

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.skillbox.skillbox.scopedstorage.network.Network
import com.skillbox.skillbox.scopedstorage.utils.haveQ
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddDialogFragmentRepository(private val context: Context) {

    suspend fun downloadVideoFromNetwork(title: String, url: String) {
        withContext(Dispatchers.IO) {
            val videoIri = saveVideoDetails(title)
            downloadVideo(url, videoIri)
            makeVideoVisible(videoIri)
        }
    }

    private fun saveVideoDetails(title: String): Uri {
        val volume = if (haveQ()) {
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        } else {
            MediaStore.VOLUME_EXTERNAL
        }
        val videoCollectionUri = MediaStore.Video.Media.getContentUri(volume)
        val videoDetails = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, title)
            put(MediaStore.Video.Media.MIME_TYPE, "video/*")
            if (haveQ()) {
                put(MediaStore.Video.Media.IS_PENDING, 1)
            }
        }
        return context.contentResolver.insert(videoCollectionUri, videoDetails)!!
    }

    private suspend fun downloadVideo(url: String, uri: Uri) {
        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            Network.api
                .getFile(url)
                .byteStream()
                .use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
        }
    }

    private fun makeVideoVisible(videoUri: Uri) {
        if (haveQ().not()) return

        val videoDetails = ContentValues().apply {
            put(MediaStore.Images.Media.IS_PENDING, 0)
        }
        context.contentResolver.update(videoUri, videoDetails, null, null)
    }
}