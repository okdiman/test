package com.skillbox.skillbox.roomdao.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.skillbox.skillbox.roomdao.database.contracts.ClubsContract
import com.skillbox.skillbox.roomdao.database.contracts.StadiumsContract
import kotlinx.android.parcel.Parcelize

//   описываем в Entity название таблицы, составной primary key, foreign key и индексы
@Entity(
    tableName = ClubsContract.TABLE_NAME,
    primaryKeys = [ClubsContract.Columns.CLUB_TITLE, ClubsContract.Columns.CITY],
    foreignKeys = [ForeignKey(
        entity = Stadiums::class,
        parentColumns = [StadiumsContract.Columns.ID],
        childColumns = [ClubsContract.Columns.STADIUM_ID]
    )],
    indices = [Index(ClubsContract.Columns.CLUB_TITLE), Index(ClubsContract.Columns.STADIUM_ID)],
    inheritSuperIndices = false
)
@Parcelize
data class Clubs(
    @ColumnInfo(name = ClubsContract.Columns.STADIUM_ID)
    val stadium_id: Long?,
    @ColumnInfo(name = ClubsContract.Columns.CLUB_TITLE)
    val title: String,
    @ColumnInfo(name = ClubsContract.Columns.CITY)
    val city: String,
    @ColumnInfo(name = ClubsContract.Columns.COUNTRY)
    val country: String,
    @ColumnInfo(name = ClubsContract.Columns.EMBLEM)
    val emblem: String?,
    @ColumnInfo(name = ClubsContract.Columns.YEAR_OF_FOUNDATION)
    val yearOfFoundation: Int?
) : Parcelable
