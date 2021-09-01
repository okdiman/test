package com.skillbox.skillbox.roomdao.fragments.stadiums

import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.connections.StadiumsWithAttendance
import com.skillbox.skillbox.roomdao.database.entities.Stadiums

class StadiumDetailsRepository {

    //    получаем инстанс Дао стадиона
    private val stadiumDao = Database.instance.stadiumsDao()

    //    получение стадиона вместе с посещаемостью
    suspend fun getStadiumAndAttendance(stadiumName: String): StadiumsWithAttendance? {
        return stadiumDao.getStadiumsWithAttendance(stadiumName)
    }

    //    удаление стадиона
    suspend fun deleteStadium(stadiums: Stadiums) {
        stadiumDao.deleteStadiums(stadiums)
    }
}