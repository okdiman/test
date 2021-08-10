package com.skillbox.skillbox.files.main

import android.app.DownloadManager
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.skillbox.skillbox.files.network.Network
import java.io.File

class MainFragmentRepository {
    suspend fun downloadFile(
        urlAddress: String,
        name: String,
        sharedPrefs: SharedPreferences,
        filesDir: File
    ): Boolean {
//        устанавливаем имя файла и адрес
        val fileName = "${System.currentTimeMillis()}_$name"
        val file = File(filesDir, fileName)
        try {
//            производит запись данных в созданный файл из скачанного файла из сети
            file.outputStream().use { fileOutputStream ->
                Network.api
                    .getFile(urlAddress)
                    .byteStream()
                    .use {
                        it.copyTo(fileOutputStream)
                    }
//                делаем запись в shared prefs о скачивании файла
                sharedPrefs.edit()
                    .putString(urlAddress, fileName)
                    .apply()
            }
            return true
        } catch (t: Throwable) {
//            в случае ошибки удаляем файл с носителя
            Log.i("download", t.toString())
            file.delete()
            return false
        }
    }

    fun downloadFileByDownloadManager(
        urlAddress: String,
        name: String,
        sharedPrefs: SharedPreferences,
        filesDir: File,
        downloadManager: DownloadManager,
        loader: ProgressBar
    ): Boolean {
//        устанавливаем имя файла и адрес
        val fileName = "${System.currentTimeMillis()}_$name"
        val file = File(filesDir, fileName)
        try {
//            создаем запрос на скачивание файла через downloadManager
            val request = DownloadManager.Request(Uri.parse(urlAddress))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationUri(Uri.fromFile(file))
                .setTitle(fileName)
                .setDescription("Downloading")
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
//            производим запрос
            val downloadID = downloadManager.enqueue(request)
//          проверка завершения
            var finishLoad = false
//          показатель прогресса
            var progress = 0
            while (!finishLoad) {
//                получение объекта курсора
                val cursor =
                    downloadManager.query(DownloadManager.Query().setFilterById(downloadID))
                Log.i("cursor", "$cursor")
                if (cursor.moveToFirst()) {
//                    активация лоадера
                    loader.isVisible = true
                    loader.progress = progress
                    Log.i("cursor", "${cursor.moveToFirst()}")
                    Log.i("cursor", "${cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)}")
                    Log.i(
                        "cursor",
                        "${cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)}"
                    )
                    Log.i(
                        "cursor",
                        "${cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)}"
                    )
//                   проверка статуса загрузки
                    when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                        DownloadManager.STATUS_FAILED -> {
                            finishLoad = true
                        }
                        DownloadManager.STATUS_PAUSED -> break
                        DownloadManager.STATUS_PENDING -> break
                        DownloadManager.STATUS_RUNNING -> {
//                            получение общего объема файла
                            val total =
                                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                            if (total >= 0) {
//                               получение скаченного объема
                                val downloaded =
                                    cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
//                               расчет прогресса
                                progress = ((downloaded * 100L) / total).toInt()

                            }
                        }
                        DownloadManager.STATUS_SUCCESSFUL -> {
                            loader.isVisible = false
                            progress = 100
                            finishLoad = true
                        }
                    }
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


    companion object {
        const val SHARED_PREF = "Shared preferences"
        const val FILES_DIR_NAME = "Folder for downloads files"
        const val FIRST_RUN = "First run"
    }
}