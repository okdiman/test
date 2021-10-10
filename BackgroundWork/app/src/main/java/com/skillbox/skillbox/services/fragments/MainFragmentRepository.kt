package com.skillbox.skillbox.services.fragments

import android.content.Context
import androidx.work.*
import com.skillbox.skillbox.services.worker.DownloadWorker
import java.util.concurrent.TimeUnit

class MainFragmentRepository(private val context: Context) {
    fun downloadFile(url: String) {
        val workData = workDataOf(
            DownloadWorker.URL_WORKER_KEY to url
        )
        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(workData)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.SECONDS)
            .setConstraints(workConstraints)
            .build()
        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                DownloadWorker.UNIQUE_DOWNLOADING,
                ExistingWorkPolicy.KEEP,
                workRequest
            )
    }
}