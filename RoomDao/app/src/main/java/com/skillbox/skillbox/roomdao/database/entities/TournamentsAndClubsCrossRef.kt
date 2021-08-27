package com.skillbox.skillbox.roomdao.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.skillbox.skillbox.roomdao.database.contracts.TournamentAndClubsCrossRefContract

@Entity(
    tableName = TournamentAndClubsCrossRefContract.TABLE_NAME,
    primaryKeys = [TournamentAndClubsCrossRefContract.Columns.TOURNAMENT_ID, TournamentAndClubsCrossRefContract.Columns.CLUB_ID]
)
data class TournamentsAndClubsCrossRef(
    @ColumnInfo(name = TournamentAndClubsCrossRefContract.Columns.TOURNAMENT_ID)
    val tournamentId: Long,
    @ColumnInfo(name = TournamentAndClubsCrossRefContract.Columns.CLUB_ID)
    val clubId: Long
)
