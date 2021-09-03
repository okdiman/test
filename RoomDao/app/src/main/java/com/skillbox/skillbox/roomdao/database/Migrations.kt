package com.skillbox.skillbox.roomdao.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE attendance ADD COLUMN count_of_matches INTEGER DEFAULT 0")
    }
}