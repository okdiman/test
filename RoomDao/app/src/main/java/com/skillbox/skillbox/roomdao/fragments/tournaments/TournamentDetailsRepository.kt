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

    suspend fun getTournamentWithClubs(tournamentId: Long): TournamentsWithClubs? {
        return tournamentsDao.getTournamentWithClubs(tournamentId)
    }

    suspend fun getAllClubs(): List<Clubs> {
        return clubsDao.getAllClubs()
    }

    suspend fun deleteTournament(tournament: Tournaments){
        tournamentsDao.deleteTournament(tournament)
    }

    suspend fun updateTournament(
        tournament: Tournaments,
        tournamentsAndClubsCrossRef: TournamentsAndClubsCrossRef
    ) {
        Database.instance.withTransaction {
            tournamentsDao.updateTournament(tournament)
            tournamentsDao.updateClubsList(tournamentsAndClubsCrossRef)
        }
    }
}