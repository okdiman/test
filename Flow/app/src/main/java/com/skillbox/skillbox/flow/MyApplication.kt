package com.skillbox.skillbox.flow

import android.app.Application
import com.skillbox.skillbox.flow.database.Database

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }
}