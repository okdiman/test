package com.skillbox.skillbox.services.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import androidx.core.app.NotificationManagerCompat

object NotificationChannels {
    const val MAIN_CHANNEL_ID = "main_channel"

    fun create(context: Context) {
        createMainChannel(context)
    }

    private fun createMainChannel(context: Context) {
        val name = "Main channel"
        val descriptionOfChannel = "Main channel for all notifications"
        val priority = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(MAIN_CHANNEL_ID, name, priority).apply {
            description = descriptionOfChannel
//            активируем вибрацию
            enableVibration(true)
//            активируем светодиоды
            enableLights(true)
//            создаем паттер для вибрации
            vibrationPattern = longArrayOf(200, 200, 500, 500)
//            устанавливаем звук
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
        }
        NotificationManagerCompat.from(context)
            .createNotificationChannel(channel)
    }
}