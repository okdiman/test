package com.skillbox.skillbox.services.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.skillbox.skillbox.services.network.Network
import java.io.File

class DownloadWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val url = inputData.getString(URL_WORKER_KEY)
        val fileDir = context.getExternalFilesDir(FOLDER_FOR_DOWNLOAD)
        val name = url!!.substring(
            url.lastIndexOf('/') + 1,
            url.length
        )
        val fullName = "${System.currentTimeMillis()}_$name"
        val file = File(fileDir, fullName)
        return try {
            file.outputStream().use { fileOutPutStream ->
                Network.api
                    .downloadFile(url)
                    .byteStream()
                    .use {
                        it.copyTo(fileOutPutStream)
                    }
            }
            Result.success()
        } catch (t: Throwable) {
            Result.failure()
        }
    }

    companion object {
        const val URL_WORKER_KEY = "url_worker_key"
        const val FOLDER_FOR_DOWNLOAD = "Folder for downloading files"
        const val UNIQUE_DOWNLOADING = "unique_downloading"
    }

}