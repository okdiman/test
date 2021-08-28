package com.skillbox.skillbox.roomdao.database.contracts

object ClubsContract {
    const val TABLE_NAME = "clubs"
    object Columns {
        const val STADIUM_ID = "stadium_id"
        const val CLUB_TITLE = "club_title"
        const val CITY = "city"
        const val COUNTRY = "country"
        const val EMBLEM = "emblem"
        const val YEAR_OF_FOUNDATION = "year_of_foundation"
    }
}