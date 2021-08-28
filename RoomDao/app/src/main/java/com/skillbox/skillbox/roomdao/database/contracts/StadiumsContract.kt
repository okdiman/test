package com.skillbox.skillbox.roomdao.database.contracts

object StadiumsContract {
    const val TABLE_NAME = "stadiums"

    object Columns {
        const val ID = "id"
        const val STADIUM_NAME = "stadium_name"
        const val STADIUM_PICTURE = "stadium_picture"
        const val CAPACITY = "capacity"
        const val YEAR_OF_BUILD = "year_of_build"
    }
}