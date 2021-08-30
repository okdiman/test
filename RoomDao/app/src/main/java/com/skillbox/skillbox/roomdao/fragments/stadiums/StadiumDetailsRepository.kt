package com.skillbox.skillbox.roomdao.fragments.stadiums

import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.entities.Stadiums

class StadiumDetailsRepository {
    private val stadiumDao = Database.instance.stadiumsDao()
    suspend fun getStadiumAndAttendance(stadiumName: String): Stadiums {
        return stadiumDao.getStadiumByName(stadiumName)
    }

    suspend fun deleteStadium(stadiums: Stadiums) {
        stadiumDao.deleteStadiums(stadiums)
    }
}