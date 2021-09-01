package com.skillbox.skillbox.roomdao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skillbox.skillbox.roomdao.database.dao.ClubsDao
import com.skillbox.skillbox.roomdao.database.dao.StadiumsDao
import com.skillbox.skillbox.roomdao.database.dao.TournamentsAndClubsDao
import com.skillbox.skillbox.roomdao.database.dao.TournamentsDao
import com.skillbox.skillbox.roomdao.database.entities.*

@Database(
//    указываем все entities и версию БД
    entities = [
        Attendance::class,
        Clubs::class,
        Stadiums::class,
        Tournaments::class,
        TournamentsAndClubsCrossRef::class
    ], version = AppDatabase.DB_VERSION
)
//    создаем астратный класс БД и абстрактные методы для всех Дао. Рум реализует их автоматически
abstract class AppDatabase : RoomDatabase() {
    abstract fun clubsDao(): ClubsDao
    abstract fun stadiumsDao(): StadiumsDao
    abstract fun tournamentsDao(): TournamentsDao
    abstract fun tournamentsWithClubsDao(): TournamentsAndClubsDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "app-database"
    }
}