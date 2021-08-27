package com.skillbox.skillbox.roomdao.database.connections

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.skillbox.skillbox.roomdao.database.contracts.ClubsContract
import com.skillbox.skillbox.roomdao.database.contracts.TournamentContract
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Tournaments
import com.skillbox.skillbox.roomdao.database.entities.TournamentsAndClubsCrossRef

data class TournamentsWithClubs(
    @Embedded
    val tournaments: Tournaments,
    @Relation(
        parentColumn = TournamentContract.Columns.ID,
        entityColumn = ClubsContract.Columns.CITY + ClubsContract.Columns.CITY,
        associateBy = Junction(TournamentsAndClubsCrossRef::class)
    )
    val clubs: List<Clubs>
)