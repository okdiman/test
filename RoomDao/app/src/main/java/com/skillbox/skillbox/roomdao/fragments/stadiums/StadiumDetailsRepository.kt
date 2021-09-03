package com.skillbox.skillbox.roomdao.fragments.stadiums

import androidx.room.withTransaction
import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.connections.StadiumsWithAttendance
import com.skillbox.skillbox.roomdao.database.entities.Attendance
import com.skillbox.skillbox.roomdao.database.entities.Stadiums

class StadiumDetailsRepository {

    //    получаем инстанс Дао стадиона
    private val stadiumDao = Database.instance.stadiumsDao()

    private val attendanceDao = Database.instance.attendanceDao()

    //    получение стадиона вместе с посещаемостью
    suspend fun getStadiumAndAttendance(stadiumName: String): StadiumsWithAttendance? {
        return stadiumDao.getStadiumsWithAttendance(stadiumName)
    }

    //    удаление стадиона
    suspend fun deleteStadium(stadiums: Stadiums) {
        Database.instance.withTransaction {
            attendanceDao.deleteAttendance(stadiums.id)
            stadiumDao.deleteStadiums(stadiums)
        }
    }

    suspend fun changeAttendance(attendance: Attendance) {
        Database.instance.withTransaction {
            attendanceDao.addAttendanceToClub(attendance)
        }
    }
}