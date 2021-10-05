package com.skillbox.skillbox.flow.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
//    перечисляем все entities
    entities = [MovieEntity::class],
    version = AppDatabase.DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    //    создаем абстрактную реализацию MovieDao
    abstract fun movieDao(): MovieDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "movie-database"
    }
}