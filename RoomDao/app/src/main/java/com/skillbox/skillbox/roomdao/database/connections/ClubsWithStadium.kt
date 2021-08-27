package com.skillbox.skillbox.roomdao.database.connections

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.skillbox.roomdao.database.contracts.ClubsContract
import com.skillbox.skillbox.roomdao.database.contracts.StadiumsContract
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Stadiums

data class ClubsWithStadium(
    @Embedded
    val club: Clubs,
    @Relation(
        parentColumn = ClubsContract.Columns.STADIUM_ID,
        entityColumn = StadiumsContract.Columns.ID
    )
    val stadium: Stadiums
)
