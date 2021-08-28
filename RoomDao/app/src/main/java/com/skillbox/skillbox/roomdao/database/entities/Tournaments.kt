package com.skillbox.skillbox.roomdao.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.skillbox.skillbox.roomdao.database.TournamentsTypeConverter
import com.skillbox.skillbox.roomdao.database.TypeOfTournament
import com.skillbox.skillbox.roomdao.database.contracts.TournamentContract

@Entity(tableName = TournamentContract.TABLE_NAME)
@TypeConverters(TournamentsTypeConverter::class)
data class Tournaments(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TournamentContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = TournamentContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = TournamentContract.Columns.TYPE)
    val type: TypeOfTournament,
    @ColumnInfo(name = TournamentContract.Columns.PRIZE_MONEY)
    val prizeMoney: Int?,
    @ColumnInfo(name = TournamentContract.Columns.CUP_PICTURE)
    val cupPicture: String,
    @ColumnInfo(name = TournamentContract.Columns.CLUBS_COUNT)
    val clubsCount: Int
)