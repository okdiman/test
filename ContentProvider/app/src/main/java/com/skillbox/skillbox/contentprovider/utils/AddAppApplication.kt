package com.skillbox.skillbox.contentprovider.utils

import android.app.Application
import android.util.Log

class AddAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i("provider", "started app")
    }
}