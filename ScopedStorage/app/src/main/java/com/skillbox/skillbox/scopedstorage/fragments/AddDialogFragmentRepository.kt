package com.skillbox.skillbox.scopedstorage.fragments

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import com.skillbox.skillbox.scopedstorage.network.Network
import com.skillbox.skillbox.scopedstorage.utils.haveQ

class AddDialogFragmentRepository(private val context: Context) {

    suspend fun downloadVideoFromNetwork(title: String, url: String, uri: Uri?): Boolean {
        val mt = MimeTypeMap.getSingleton()
        val mimeType = MimeTypeMap.getFileExtensionFromUrl(url).apply {
            mt.getMimeTypeFromExtension(this)
        }
        Log.i("mimetype", mimeType)
        if (mimeType == "video/*") {
            val videoUri: Uri = uri ?: saveVideoDetails(title)
            return try {
                downloadVideo(url, videoUri)
                makeVideoVisible(videoUri)
                true
            } catch (t: Throwable) {
                context.contentResolver.delete(
                    videoUri,
                    null
                )
                false
            }
        }
        return false
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