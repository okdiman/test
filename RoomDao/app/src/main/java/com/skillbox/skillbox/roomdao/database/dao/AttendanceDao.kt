package com.skillbox.skillbox.roomdao.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skillbox.skillbox.roomdao.database.contracts.AttendanceContract
import com.skillbox.skillbox.roomdao.database.entities.Attendance

@Dao
interface AttendanceDao {

    //    добавление посещаемости к клубу
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAttendanceToClub(attendance: Attendance)

    //    удаление посещаемости у клуба
    @Query("DELETE FROM ${AttendanceContract.TABLE_NAME} WHERE ${AttendanceContract.Columns.STADIUM_ID} = :stadiumId")
    suspend fun deleteAttendance(stadiumId: Long)
}