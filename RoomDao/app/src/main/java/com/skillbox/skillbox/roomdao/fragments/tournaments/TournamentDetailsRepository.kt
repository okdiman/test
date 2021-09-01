package com.skillbox.skillbox.roomdao.fragments.tournaments

import androidx.room.withTransaction
import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.connections.TournamentsWithClubs
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Tournaments
import com.skillbox.skillbox.roomdao.database.entities.TournamentsAndClubsCrossRef


class TournamentDetailsRepository {

    //    получаем инстансы необходимых Дао
    private val tournamentsDao = Database.instance.tournamentsDao()
    private val clubsDao = Database.instance.clubsDao()
    private val tournamentsWithClubsDao = Database.instance.tournamentsWithClubsDao()

    //    получаем турнир со списком клубов-участников
    suspend fun getTournamentWithClubs(tournamentId: Long): TournamentsWithClubs? {
        return tournamentsDao.getTournamentWithClubs(tournamentId)
    }

    //    получаем список всех клубов
    suspend fun getAllClubs(): List<Clubs> {
        return clubsDao.getAllClubs()
    }

    //    удалаяем турнир и его вспомогатлеьную таблицу
    suspend fun deleteTournament(tournament: Tournaments) {
        Database.instance.withTransaction {
            tournamentsDao.deleteTournament(tournament)
            tournamentsWithClubsDao.deleteTournamentAndClubsList(tournament.id)
        }
    }

    //    ообновляем турнир и его вспомогательную таблицу
    suspend fun updateTournament(
        tournament: Tournaments,
        tournamentsAndClubsCrossRef: TournamentsAndClubsCrossRef
    ) {
        Database.instance.withTransaction {
            tournamentsDao.updateTournament(tournament)
            tournamentsWithClubsDao.insertClubsList(tournamentsAndClubsCrossRef)
        }
    }

    //    получаем вспомогательную таблицу турнира
    suspend fun gettingCrossTableForTournament(tournamentId: Long): TournamentsAndClubsCrossRef? {
        return tournamentsWithClubsDao.getTournamentAndClubsList(tournamentId)
    }
}