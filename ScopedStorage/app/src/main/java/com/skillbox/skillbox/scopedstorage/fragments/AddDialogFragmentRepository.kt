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

    //    загрузка видео из сети
    suspend fun downloadVideoFromNetwork(title: String, url: String, uri: Uri?): Boolean {
//    создаем синглтон объект MimeTypeMap
        val mimeTypeMap = MimeTypeMap.getSingleton()
//    получаем mimeType видео по нашему Url
        val mimeType = MimeTypeMap.getFileExtensionFromUrl(url).apply {
            mimeTypeMap.getMimeTypeFromExtension(this)
        }
        Log.i("mimetype", mimeType)
//    проверяем является ли файл по ссылке видео файлом
        if (mimeType == "video/*") {
//            задаем uri для видео в зависимости от того, пришло оно к нам нуллом или нет
            val videoUri: Uri = uri ?: saveVideoDetails(title)
            return try {
//                загружаем видео и делаем его видимым для пользователя
                downloadVideo(url, videoUri)
                makeVideoVisible(videoUri)
                true
            } catch (t: Throwable) {
//                в случае ошибки удаляем созданный под видео файл
                context.contentResolver.delete(
                    videoUri,
                    null,
                    null
                )
                false
            }
        }
        return false
    }

    //    сохраняем необходимый параметры видео
    private fun saveVideoDetails(title: String): Uri {
//    елси у пользователя android 10 и выше, то явно указываем хранилище для сохранения, если нет, то просто внешнее хранилище
        val volume = if (haveQ()) {
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        } else {
            MediaStore.VOLUME_EXTERNAL
        }
//    получаем uri пути сохранения
        val videoCollectionUri = MediaStore.Video.Media.getContentUri(volume)
//    создаем объект ContentValues для видео файла
        val videoDetails = ContentValues().apply {
//            кладем название файла
            put(MediaStore.Video.Media.DISPLAY_NAME, title)
//            указываем тип файла (видео)
            put(MediaStore.Video.Media.MIME_TYPE, "video/*")
//            если у пользователя версия android 10 и выше, то устанавливаем флаг загрузки
            if (haveQ()) {
                put(MediaStore.Video.Media.IS_PENDING, 1)
            }
        }
//        возвращаем uri, по которому будет сохранен видео-файл
        return context.contentResolver.insert(videoCollectionUri, videoDetails)!!
    }

    //    загрузка видео
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