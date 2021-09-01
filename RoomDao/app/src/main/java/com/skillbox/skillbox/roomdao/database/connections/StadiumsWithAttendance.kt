package com.skillbox.skillbox.roomdao.database.connections

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.skillbox.roomdao.database.contracts.AttendanceContract
import com.skillbox.skillbox.roomdao.database.contracts.StadiumsContract
import com.skillbox.skillbox.roomdao.database.entities.Attendance
import com.skillbox.skillbox.roomdao.database.entities.Stadiums

data class StadiumsWithAttendance(
//    объявляем главным класс стадиона
    @Embedded
    val stadium: Stadiums,
//    объявляем отношения между двумя классами
    @Relation(
        parentColumn = StadiumsContract.Columns.ID,
        entityColumn = AttendanceContract.Columns.STADIUM_ID
    )
    val attendance: Attendance?
)
