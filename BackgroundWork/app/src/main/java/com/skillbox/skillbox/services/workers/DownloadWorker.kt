package com.skillbox.skillbox.services.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.skillbox.skillbox.services.network.Network
import java.io.File

class DownloadWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
//        создаем все переменные, необходимые для сохранения файла
        val url = inputData.getString(URL_WORKER_KEY)
//        выбираем директорию для сохранения
        val fileDir = context.getExternalFilesDir(FOLDER_FOR_DOWNLOAD)
        val name = url!!.substring(
            url.lastIndexOf('/') + 1,
            url.length
        )
        val fullName = "${System.currentTimeMillis()}_$name"
//        создаем объект файла в памяти телефона
        val file = File(fileDir, fullName)
        return try {
//            открываем входящий поток для нашего созданного в телефоне файла
            file.outputStream().use { fileOutPutStream ->
//                делаем запрос через сеть на скачивание файла
                Network.api
                    .downloadFile(url)
                    .byteStream()
//                        открываем исходящий поток скачиваемого файла
                    .use {
//                        записываем скачиваемый файл в наш созданный файл в телефоне
                        it.copyTo(fileOutPutStream)
                    }
            }
//            возвращаем успешный результат
            Result.success()
        } catch (t: Throwable) {
//            в случае ошибки удаляем изначально созданный файл и возвращаем неудачный рещультат
            file.delete()
            Result.failure()
        }
    }

    companion object {
        const val URL_WORKER_KEY = "url_worker_key"
        const val FOLDER_FOR_DOWNLOAD = "Folder for downloading files"
        const val UNIQUE_DOWNLOADING = "unique_downloading"
    }

}