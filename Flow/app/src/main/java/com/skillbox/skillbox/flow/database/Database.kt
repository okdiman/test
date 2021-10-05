package com.skillbox.skillbox.flow.database

import android.content.Context
import androidx.room.Room

object Database {
    //    lateinit инстанс ДБ для последующей с ним работы из др классов
    lateinit var instance: AppDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        )
            .build()
    }
}