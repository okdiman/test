package com.skillbox.skillbox.roomdao.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.skillbox.skillbox.roomdao.database.contracts.ClubsContract
import com.skillbox.skillbox.roomdao.database.contracts.StadiumsContract

@Entity(
    tableName = ClubsContract.TABLE_NAME,
    primaryKeys = [ClubsContract.Columns.TITLE, ClubsContract.Columns.CITY],
    foreignKeys = [ForeignKey(
        entity = Stadiums::class,
        parentColumns = [StadiumsContract.Columns.ID],
        childColumns = [ClubsContract.Columns.STADIUM_ID]
    )],
    indices = [Index(ClubsContract.Columns.TITLE)], inheritSuperIndices = false
)
data class Clubs(
    @ColumnInfo(name = ClubsContract.Columns.STADIUM_ID)
    val stadium_id: Long,
    @ColumnInfo(name = ClubsContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = ClubsContract.Columns.CITY)
    val city: String,
    @ColumnInfo(name = ClubsContract.Columns.COUNTRY)
    val country: String,
    @ColumnInfo(name = ClubsContract.Columns.EMBLEM)
    val emblem: String?,
    @ColumnInfo(name = ClubsContract.Columns.YEAR_OF_FOUNDATION)
    val yearOfFoundation: Int?
)
