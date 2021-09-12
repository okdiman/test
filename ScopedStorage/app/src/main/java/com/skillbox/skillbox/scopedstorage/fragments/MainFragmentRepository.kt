package com.skillbox.skillbox.scopedstorage.fragments

import android.content.ContentUris
import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import com.skillbox.skillbox.scopedstorage.classes.VideoForList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainFragmentRepository(private val context: Context) {
    //    создаем обсервер для MediaStore
    private var observer: ContentObserver? = null

    //    подписка на обновление списка видео в MediaStore
    fun observeVideos(onChange: () -> Unit) {
//        инициализируем обсервер
        observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
//                передаем лямбду
                onChange()
            }
        }
//        регистрируем обсервер
        context.contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true,
            observer!!
        )
    }

    //    описка от обсервера
    fun unregisterObserver() {
        observer?.let { context.contentResolver.unregisterContentObserver(it) }
    }

    //    получение списка всех видео
    suspend fun getVideoList(): List<VideoForList> {
//    создаем список видео
        val videos = mutableListOf<VideoForList>()
//        переходим на фоновый поток для работы с файлами
        withContext(Dispatchers.IO) {
//            делаем запрос для получения всех видео файлов
            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Video.Media.DISPLAY_NAME
            )?.use { cursor ->
//                пока курсор не дошел до последней строки создаем объекты видео и кладем их в список
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

    //    удаление видео по id
    suspend fun deleteVideo(id: Long) {
        withContext(Dispatchers.IO) {
//            получаем uri нужного нам видео
            val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
//            удаляем видео по uri
            context.contentResolver.delete(uri, null, null)
        }
    }
}