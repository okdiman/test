package com.skillbox.skillbox.roomdao.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.skillbox.skillbox.roomdao.database.contracts.TournamentAndClubsCrossRefContract

@Entity(
    tableName = TournamentAndClubsCrossRefContract.TABLE_NAME,
    primaryKeys = [TournamentAndClubsCrossRefContract.Columns.TOURNAMENT_ID, TournamentAndClubsCrossRefContract.Columns.CLUB_TITLE],
    indices = [Index(TournamentAndClubsCrossRefContract.Columns.CLUB_TITLE)]
)
data class TournamentsAndClubsCrossRef(
    @ColumnInfo(name = TournamentAndClubsCrossRefContract.Columns.TOURNAMENT_ID)
    val tournamentId: Long,
    @ColumnInfo(name = TournamentAndClubsCrossRefContract.Columns.CLUB_TITLE)
    val clubTitle: String
)
