package com.skillbox.skillbox.notifications

import android.app.Application
import com.skillbox.skillbox.notifications.notifications.NotificationChannels

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
//        создаем каналы при запуске приложения
        NotificationChannels.create(this)
    }
}