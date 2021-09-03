package com.skillbox.skillbox.roomdao.database

import android.content.Context
import androidx.room.Room

object Database {

    //    создаем late init инстанс для последующего обращения к нему, в момент, когда контекст будут доступен
    lateinit var instance: AppDatabase
        private set

    //    инициализируем БД
    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}