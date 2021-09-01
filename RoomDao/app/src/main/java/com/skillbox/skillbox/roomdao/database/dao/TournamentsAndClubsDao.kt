package com.skillbox.skillbox.roomdao.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.skillbox.skillbox.roomdao.database.contracts.TournamentAndClubsCrossRefContract
import com.skillbox.skillbox.roomdao.database.entities.TournamentsAndClubsCrossRef

@Dao
interface TournamentsAndClubsDao {
    //    создание элемента вспомогательной таблицы турнир/клуб
    @Insert
    suspend fun insertClubsList(tournamentsAndClubsCrossRef: TournamentsAndClubsCrossRef)

    //    обновление данных вспомогательной таблицы турнир/клуб
    @Update
    suspend fun updateClubsList(tournamentsAndClubsCrossRef: TournamentsAndClubsCrossRef)

    //    удаление данных вспомогательной таблицы турнир/клубдля клуба
    @Query(
        "DELETE FROM ${TournamentAndClubsCrossRefContract.TABLE_NAME} " +
                "WHERE ${TournamentAndClubsCrossRefContract.Columns.TOURNAMENT_ID} = :tournamentId"
    )
    suspend fun deleteTournamentAndClubsListByTournament(tournamentId: Long)

    //    удаление данных вспомогательной таблицы турнир/клуб
    @Query(
        "DELETE FROM ${TournamentAndClubsCrossRefContract.TABLE_NAME} " +
                "WHERE ${TournamentAndClubsCrossRefContract.Columns.CLUB_TITLE} = :clubTitle"
    )
    suspend fun deleteTournamentAndClubsListByClub(clubTitle: String)

    //    удаление данныхвсех вспомогательных таблиц турнир/клуб
    @Query("DELETE FROM ${TournamentAndClubsCrossRefContract.TABLE_NAME}")
    suspend fun deleteAllTournamentAndClubsList()

    @Query(
        "SELECT * FROM ${TournamentAndClubsCrossRefContract.TABLE_NAME} " +
                "WHERE ${TournamentAndClubsCrossRefContract.Columns.TOURNAMENT_ID} = :tournamentId"
    )
    suspend fun getTournamentAndClubsList(tournamentId: Long): TournamentsAndClubsCrossRef?
}