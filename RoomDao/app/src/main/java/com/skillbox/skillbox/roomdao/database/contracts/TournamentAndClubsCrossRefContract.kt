package com.skillbox.skillbox.roomdao.database.contracts


object TournamentAndClubsCrossRefContract {
    const val TABLE_NAME = "tournaments_and_clubs_cross_ref"

    object Columns {
        const val TOURNAMENT_ID ="tournament_id"
        const val CLUB_TITLE = "club_title"
    }
}