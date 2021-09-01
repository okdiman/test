package com.skillbox.skillbox.roomdao.fragments.tournaments

import androidx.room.withTransaction
import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.entities.Tournaments

class TournamentsRepository {
    private val tournamentDao = Database.instance.tournamentsDao()
    private val tournamentsWithClubsDao = Database.instance.tournamentsWithClubsDao()

    suspend fun saveNewTournament(tournaments: Tournaments) {
        tournamentDao.addNewTournament(listOf(tournaments))
    }

    suspend fun deleteAllTournaments() {
        Database.instance.withTransaction {
            tournamentDao.deleteAllTournaments()
            tournamentsWithClubsDao.deleteAllTournamentAndClubsList()
        }
    }

    suspend fun getAllTournaments(): List<Tournaments> {
        return tournamentDao.getAllTournaments()
    }
}