package com.skillbox.skillbox.scopedstorage.fragments

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.skillbox.skillbox.scopedstorage.classes.VideoForList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainFragmentRepository(private val context: Context) {
    suspend fun getVideoList(): List<VideoForList> {
        val videos = mutableListOf<VideoForList>()
        withContext(Dispatchers.IO) {
            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Video.Media.DISPLAY_NAME
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val uri =
                        ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                    val title =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
                    val size = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                    videos += VideoForList(id, uri, title, size)
                }
            }
        }
        return videos
    }
}