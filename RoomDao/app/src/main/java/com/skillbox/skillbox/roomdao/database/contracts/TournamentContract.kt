package com.skillbox.skillbox.roomdao.database.contracts

object TournamentContract {
    const val TABLE_NAME = "tournaments"

    object Columns {
        const val ID = "tournament_id"
        const val TITLE = "title"
        const val TYPE = "type"
        const val PRIZE_MONEY = "prize_money"
        const val CUP_PICTURE = "cup_picture"
        const val CLUBS_COUNT = "clubs_count"
    }
}