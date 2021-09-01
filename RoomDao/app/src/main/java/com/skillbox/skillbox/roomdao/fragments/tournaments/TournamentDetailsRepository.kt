package com.skillbox.skillbox.roomdao.fragments.tournaments

import androidx.room.withTransaction
import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.connections.TournamentsWithClubs
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Tournaments
import com.skillbox.skillbox.roomdao.database.entities.TournamentsAndClubsCrossRef


class TournamentDetailsRepository {

    private val tournamentsDao = Database.instance.tournamentsDao()
    private val clubsDao = Database.instance.clubsDao()
    private val tournamentsWithClubsDao = Database.instance.tournamentsWithClubsDao()

    suspend fun getTournamentWithClubs(tournamentId: Long): TournamentsWithClubs? {
        return tournamentsDao.getTournamentWithClubs(tournamentId)
    }

    suspend fun getAllClubs(): List<Clubs> {
        return clubsDao.getAllClubs()
    }

    suspend fun deleteTournament(tournament: Tournaments) {
        tournamentsDao.deleteTournament(tournament)
        tournamentsWithClubsDao.deleteTournamentAndClubsList(tournament.id)
    }

    suspend fun updateTournament(
        tournament: Tournaments,
        tournamentsAndClubsCrossRef: TournamentsAndClubsCrossRef
    ) {
        Database.instance.withTransaction {
            tournamentsDao.updateTournament(tournament)
            tournamentsWithClubsDao.insertClubsList(tournamentsAndClubsCrossRef)
        }
    }

    suspend fun gettingCrossTableForTournament(tournamentId: Long): TournamentsAndClubsCrossRef? {
        return tournamentsWithClubsDao.getTournamentAndClubsList(tournamentId)
    }
}