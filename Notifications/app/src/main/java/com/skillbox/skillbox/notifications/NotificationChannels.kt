package com.skillbox.skillbox.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

object NotificationChannels {

    const val MESSAGE_CHANNEL_ID = "messages"
    const val NEWS_CHANNEL_ID = "news"

    fun create(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createMessagesChannel(context)
            createNewsChannel(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createMessagesChannel(context: Context) {
        val name = "Messages"
        val channelDescription = "Urgent messages"
        val priority = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(MESSAGE_CHANNEL_ID, name, priority).apply {
            enableVibration(true)
            enableLights(true)
            vibrationPattern = longArrayOf(200, 200, 500, 500)
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
            description = channelDescription
        }
        NotificationManagerCompat.from(context)
            .createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNewsChannel(context: Context) {
        val name = "News"
        val channelDescription = "News, advertising, updates"
        val priority = NotificationManager.IMPORTANCE_LOW

        val channel = NotificationChannel(NEWS_CHANNEL_ID, name, priority).apply {
            description = channelDescription
        }
        NotificationManagerCompat.from(context)
            .createNotificationChannel(channel)
    }
}