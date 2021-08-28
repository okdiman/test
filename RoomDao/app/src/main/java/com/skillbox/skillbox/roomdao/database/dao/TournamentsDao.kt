package com.skillbox.skillbox.roomdao.database.dao

import androidx.room.*
import com.skillbox.skillbox.roomdao.database.connections.TournamentsWithClubs
import com.skillbox.skillbox.roomdao.database.contracts.TournamentContract
import com.skillbox.skillbox.roomdao.database.entities.Tournaments

@Dao
interface TournamentsDao {
    @Query("SELECT * FROM ${TournamentContract.TABLE_NAME}")
    suspend fun getAllTournaments(): List<Tournaments>

    @Transaction
    @Query("SELECT * FROM ${TournamentContract.TABLE_NAME} WHERE  ${TournamentContract.Columns.ID} = :tournamentId")
    suspend fun getTournamentWithClubs(tournamentId: Long): TournamentsWithClubs?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewTournament(tournaments: List<Tournaments>)

    @Update
    suspend fun updateTournament(tournaments: Tournaments)

    @Delete
    suspend fun deleteTournament(tournaments: Tournaments)

    @Query("DELETE FROM ${TournamentContract.TABLE_NAME}")
    suspend fun deleteAllTournaments()
}