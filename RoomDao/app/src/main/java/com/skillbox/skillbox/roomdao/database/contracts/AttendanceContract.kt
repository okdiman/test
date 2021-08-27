package com.skillbox.skillbox.roomdao.database.contracts

object AttendanceContract {
    const val TABLE_NAME = "attendance"

    object Columns {
        const val ID = "id"
        const val STADIUM_ID = "stadium_id"
        const val AVERAGE_ATTENDANCE = "average_attendance"
    }
}