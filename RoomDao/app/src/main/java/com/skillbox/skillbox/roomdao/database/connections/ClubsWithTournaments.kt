package com.skillbox.skillbox.roomdao.database.connections

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.skillbox.skillbox.roomdao.database.contracts.ClubsContract
import com.skillbox.skillbox.roomdao.database.contracts.TournamentAndClubsCrossRefContract
import com.skillbox.skillbox.roomdao.database.contracts.TournamentContract
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Tournaments
import com.skillbox.skillbox.roomdao.database.entities.TournamentsAndClubsCrossRef

data class ClubsWithTournaments(
    @Embedded
    val club: Clubs,
    @Relation(
        parentColumn = ClubsContract.Columns.CLUB_TITLE,
        entityColumn = TournamentAndClubsCrossRefContract.Columns.TOURNAMENT_ID,
        associateBy = Junction(TournamentsAndClubsCrossRef::class)
    )
    val tournaments: List<Tournaments>
)
