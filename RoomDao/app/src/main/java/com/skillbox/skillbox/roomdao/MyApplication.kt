package com.skillbox.skillbox.roomdao

import android.app.Application
import com.skillbox.skillbox.roomdao.database.Database

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
//        инициализируем БД
        Database.init(this)
    }
}