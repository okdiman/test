package com.skillbox.skillbox.location

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class LocationApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}