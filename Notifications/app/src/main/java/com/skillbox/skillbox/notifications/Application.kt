package com.skillbox.skillbox.notifications

import android.app.Application

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationChannels.create(this)
    }
}