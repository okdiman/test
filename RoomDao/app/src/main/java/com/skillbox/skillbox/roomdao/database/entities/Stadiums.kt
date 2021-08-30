package com.skillbox.skillbox.roomdao.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skillbox.skillbox.roomdao.database.contracts.StadiumsContract

@Entity(tableName = StadiumsContract.TABLE_NAME)
data class Stadiums(
    @ColumnInfo(name = StadiumsContract.Columns.ID)
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = StadiumsContract.Columns.STADIUM_NAME)
    val stadiumName: String,
    @ColumnInfo(name = StadiumsContract.Columns.STADIUM_PICTURE)
    val stadiumPicture: String?,
    @ColumnInfo(name = StadiumsContract.Columns.CAPACITY)
    val capacity: Int,
    @ColumnInfo(name = StadiumsContract.Columns.YEAR_OF_BUILD)
    val yearOfBuild: Int?
)