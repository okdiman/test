package com.skillbox.skillbox.roomdao.fragments.tournaments

import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.entities.Tournaments

class TournamentsRepository {
    private val tournamentDao = Database.instance.tournamentsDao()

    suspend fun saveNewTournament(tournaments: Tournaments) {
        tournamentDao.addNewTournament(listOf(tournaments))
    }

    suspend fun deleteAllTournaments() {
        tournamentDao.deleteAllTournaments()
    }

    suspend fun getAllTournaments(): List<Tournaments> {
        return tournamentDao.getAllTournaments()
    }
}