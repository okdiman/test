package com.skillbox.skillbox.roomdao.database.entities

import androidx.room.*
import com.skillbox.skillbox.roomdao.database.contracts.AttendanceContract
import com.skillbox.skillbox.roomdao.database.contracts.StadiumsContract

@Entity(
    tableName = AttendanceContract.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = Stadiums::class,
        parentColumns = [StadiumsContract.Columns.ID],
        childColumns = [AttendanceContract.Columns.STADIUM_ID]
    )],
    indices = [Index(AttendanceContract.Columns.STADIUM_ID)], inheritSuperIndices = false
)
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AttendanceContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = AttendanceContract.Columns.STADIUM_ID)
    val stadiumId: Long,
    @ColumnInfo(name = AttendanceContract.Columns.AVERAGE_ATTENDANCE)
    val averageAttendance: Int?
)
