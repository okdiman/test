package com.skillbox.skillbox.roomdao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skillbox.skillbox.roomdao.database.dao.ClubsDao
import com.skillbox.skillbox.roomdao.database.dao.StadiumsDao
import com.skillbox.skillbox.roomdao.database.dao.TournamentsDao
import com.skillbox.skillbox.roomdao.database.entities.*

@Database(
    entities = [
        Attendance::class,
        Clubs::class,
        Stadiums::class,
        Tournaments::class,
        TournamentsAndClubsCrossRef::class
    ], version = AppDatabase.DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clubsDao(): ClubsDao
    abstract fun stadiumsDao(): StadiumsDao
    abstract fun tournamentsDao(): TournamentsDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "app-database"
    }
}