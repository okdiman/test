package com.skillbox.skillbox.notifications.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.skillbox.skillbox.notifications.utils.haveO

object NotificationChannels {
    //    константы для id каналов
    const val MESSAGE_CHANNEL_ID = "messages"
    const val NEWS_CHANNEL_ID = "news"

    //    создаем каналы
    fun create(context: Context) {
//    если у пользователя >=Android 8, то создаем каналы
        if (haveO()) {
            createMessagesChannel(context)
            createNewsChannel(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
//    создаем канал сообщений
    private fun createMessagesChannel(context: Context) {
        val name = "Messages"
        val channelDescription = "Urgent messages"
        val priority = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(MESSAGE_CHANNEL_ID, name, priority).apply {
//            активируем вибрацию
            enableVibration(true)
//            активируем светодиоды
            enableLights(true)
//            создаем паттер для вибрации
            vibrationPattern = longArrayOf(200, 200, 500, 500)
//            устанавливаем звук
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
//            устанавливаем сообщение внутри оповещения
            description = channelDescription
        }
//        создаем канал
        NotificationManagerCompat.from(context)
            .createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
//    создаем канал новостей
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