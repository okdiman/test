package com.skillbox.skillbox.roomdao.database.connections

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.skillbox.roomdao.database.contracts.ClubsContract
import com.skillbox.skillbox.roomdao.database.contracts.StadiumsContract
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Stadiums

data class StadiumWithClubs(
    @Embedded
    val stadium: Stadiums,
    @Relation(
        parentColumn = StadiumsContract.Columns.ID,
        entityColumn = ClubsContract.Columns.STADIUM_ID
    )
    val clubs: List<Clubs>
)
