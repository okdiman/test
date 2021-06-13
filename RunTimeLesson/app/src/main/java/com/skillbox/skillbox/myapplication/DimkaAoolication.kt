package com.skillbox.skillbox.myapplication

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class DimkaApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}