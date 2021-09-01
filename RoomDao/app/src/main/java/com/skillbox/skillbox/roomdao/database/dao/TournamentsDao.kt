package com.skillbox.skillbox.roomdao.database.dao

import androidx.room.*
import com.skillbox.skillbox.roomdao.database.connections.TournamentsWithClubs
import com.skillbox.skillbox.roomdao.database.contracts.TournamentContract
import com.skillbox.skillbox.roomdao.database.entities.Tournaments

@Dao
interface TournamentsDao {

    //    получение всех турниров
    @Query("SELECT * FROM ${TournamentContract.TABLE_NAME}")
    suspend fun getAllTournaments(): List<Tournaments>

    //    получение турнира со списком клубов. Через транзакцию для атомарности
    @Transaction
    @Query(
        "SELECT * FROM ${TournamentContract.TABLE_NAME} " +
                "WHERE  ${TournamentContract.Columns.ID} = :tournamentId"
    )
    suspend fun getTournamentWithClubs(tournamentId: Long): TournamentsWithClubs?

    //    добавление нового турнира
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewTournament(tournaments: List<Tournaments>)

    //    обновление турнира
    @Update
    suspend fun updateTournament(tournaments: Tournaments)

    //    удаление турнира
    @Delete
    suspend fun deleteTournament(tournaments: Tournaments)

    //    удаление всех турниров
    @Query("DELETE FROM ${TournamentContract.TABLE_NAME}")
    suspend fun deleteAllTournaments()

}