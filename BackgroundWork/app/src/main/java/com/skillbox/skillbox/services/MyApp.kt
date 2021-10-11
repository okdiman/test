package com.skillbox.skillbox.services

import android.app.Application
import com.skillbox.skillbox.services.notification.NotificationChannels

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationChannels.create(this)
    }
}