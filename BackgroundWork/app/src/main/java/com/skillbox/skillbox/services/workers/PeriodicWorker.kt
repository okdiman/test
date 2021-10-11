package com.skillbox.skillbox.services.workers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.skillbox.skillbox.services.MainActivity
import com.skillbox.skillbox.services.notification.NotificationChannels

class PeriodicWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
//        создаем интент для обработки клика на уведомление
        val intent = Intent(context, MainActivity::class.java)
//        создаем обертку для интента, что его мог вызвать любой другой процесс, не только наше приложение
        val pendingIntent =
            PendingIntent.getActivity(context, PENDING_INTENT_REQUEST_CODE, intent, 0)
//        создаем уведомление
        val notification = NotificationCompat.Builder(context, NotificationChannels.MAIN_CHANNEL_ID)
            .setContentTitle("Notification from periodic worker")
            .setContentText("Periodic worker is working every 15 minutes")
            .setSmallIcon(android.R.drawable.stat_notify_chat)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
//        показываем уведдомление
        NotificationManagerCompat.from(context)
            .notify(NOTIFICATION_ID, notification)
        return Result.success()
    }

    companion object {
        private const val NOTIFICATION_ID = 123
        private const val PENDING_INTENT_REQUEST_CODE = 7777
        const val PERIODIC_UNIQUE_DOWNLOADING = "periodic_unique_downloading"
    }
}