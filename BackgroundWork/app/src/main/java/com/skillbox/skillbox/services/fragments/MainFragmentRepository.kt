package com.skillbox.skillbox.services.fragments

import android.content.Context
import androidx.work.*
import com.skillbox.skillbox.services.workers.DownloadWorker
import com.skillbox.skillbox.services.workers.PeriodicWorker
import java.util.concurrent.TimeUnit

class MainFragmentRepository(private val context: Context) {
    //    загрузка файла
    fun downloadFile(url: String) {
//        создаем объект workData и кладем туда URL для скачивания файла
        val workData = workDataOf(
            DownloadWorker.URL_WORKER_KEY to url
        )
//        задаем параметры запуска задачи (безлимитный интернет и НЕнизкий уровень зарядки
        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .build()
//        создаем одноразовый запрос с типом нашего Worker'а
        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
//                кладем в него workData
            .setInputData(workData)
//                устанавливаем критерии повторов запросов при неудаче (линейная зависимость, 20 сек)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.SECONDS)
//                кладем параметры запуска
            .setConstraints(workConstraints)
            .build()
//        запускаем в workManager'е нашу задачу с уникальным ID и политикой KEEP
        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                DownloadWorker.UNIQUE_DOWNLOADING,
                ExistingWorkPolicy.KEEP,
                workRequest
            )
    }

    //    включаем передическую задачу
    fun periodicWork() {
//        создаем запрос переодической задачи
        val workRequest = PeriodicWorkRequestBuilder<PeriodicWorker>(15, TimeUnit.MINUTES)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.SECONDS)
            .build()
//        добавляем задачу в workManager
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                PeriodicWorker.PERIODIC_UNIQUE_DOWNLOADING,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }
}