package com.skillbox.skillbox.roomdao.fragments.tournaments

import androidx.room.withTransaction
import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.entities.Tournaments

class TournamentsRepository {

    //    получаем инстансы необходимых Дао
    private val tournamentDao = Database.instance.tournamentsDao()
    private val tournamentsWithClubsDao = Database.instance.tournamentsWithClubsDao()

    //    сохранение нового турнира
    suspend fun saveNewTournament(tournaments: Tournaments) {
        tournamentDao.addNewTournament(listOf(tournaments))
    }

    //    удаление всех турниров
    suspend fun deleteAllTournaments() {
        Database.instance.withTransaction {
            tournamentDao.deleteAllTournaments()
            tournamentsWithClubsDao.deleteAllTournamentAndClubsList()
        }
    }

    //    получение всех турниров
    suspend fun getAllTournaments(): List<Tournaments> {
        return tournamentDao.getAllTournaments()
    }
}