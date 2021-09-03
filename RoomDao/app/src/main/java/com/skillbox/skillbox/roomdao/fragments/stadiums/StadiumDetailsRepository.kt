package com.skillbox.skillbox.roomdao.fragments.stadiums

import androidx.room.withTransaction
import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.connections.StadiumsWithAttendance
import com.skillbox.skillbox.roomdao.database.entities.Attendance
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Stadiums

class StadiumDetailsRepository {

    //    получаем инстанс Дао стадиона
    private val stadiumDao = Database.instance.stadiumsDao()
    private val clubsDao = Database.instance.clubsDao()
    private val attendanceDao = Database.instance.attendanceDao()

    //    получение стадиона вместе с посещаемостью
    suspend fun getStadiumAndAttendance(stadiumName: String): StadiumsWithAttendance? {
        return stadiumDao.getStadiumsWithAttendance(stadiumName)
    }

    //    установка посещаемости
    suspend fun changeAttendance(attendance: Attendance) {
        Database.instance.withTransaction {
            attendanceDao.addAttendanceToClub(attendance)
        }
    }
}