package com.skillbox.skillbox.contentprovider.main

import android.app.DownloadManager
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.ProgressBar
import androidx.core.view.isVisible
import kotlinx.coroutines.delay
import java.io.File

class ShareFileFragmentRepository {
    suspend fun downloadFileByDownloadManager(
        urlAddress: String,
        name: String,
        sharedPrefs: SharedPreferences,
        filesDir: File,
        downloadManager: DownloadManager,
        loader: ProgressBar
    ): Boolean {
//      устанавливаем имя файла и адрес
        val fileName = "${System.currentTimeMillis()}_$name"
        val file = File(filesDir, fileName)
        try {
//          создаем запрос на скачивание файла через downloadManager
            val request = DownloadManager.Request(Uri.parse(urlAddress))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationUri(Uri.fromFile(file))
                .setTitle(fileName)
                .setDescription("Downloading")
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
//          производим запрос
            val downloadID = downloadManager.enqueue(request)
//          проверка статсуса загрузки
            var downloading = true
            while (downloading) {
                val query = DownloadManager.Query()
                query.setFilterById(downloadID)
//              создаем объект cursor
                val cursor = downloadManager.query(query)
                if (cursor.moveToFirst()) {
//                  получаем объем загруженных данных
                    val bytesDownloaded =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
//                  получаем общий объем данных
                    val bytesTotal =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
//                  если загрузка завершена, завершаем цикл
                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false
                    }
//                  привязываем лоадер к прогрессу
                    loader.isVisible = true
//                  рассчитываем прогресс
                    val progress = ((bytesDownloaded * 100L) / bytesTotal).toInt()
                    loader.progress = progress
                    Log.i("cursor", progress.toString())
//                  закрываем объект cursor
                    cursor.close()
//                  с небольшой задержкой закрываем лоадер, для лучшего отображения и понимания при загрузке файлов малых объемов
                    delay(500)
                    loader.isVisible = false
                }
            }
//            делаем запись в shared prefs о скачивании файла
            sharedPrefs.edit()
                .putString(urlAddress, fileName)
                .apply()
            return true
        } catch (t: Throwable) {
//            удалаяем файл в случае ошибки при скачивании
            file.delete()
            return false
        }

    }
}