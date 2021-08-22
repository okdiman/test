package com.skillbox.skillbox.contentprovideraddapp

import android.app.Application
import android.util.Log

class AddAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i("provider", "started")
    }
}