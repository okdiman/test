package com.skillbox.skillbox.roomdao.database.dao

import androidx.room.*
import com.skillbox.skillbox.roomdao.database.connections.ClubsWithTournaments
import com.skillbox.skillbox.roomdao.database.contracts.ClubsContract
import com.skillbox.skillbox.roomdao.database.entities.Clubs

@Dao
interface ClubsDao {

    //    получение списка всех клубов, сортированного по стране и названиям
    @Query("SELECT * FROM ${ClubsContract.TABLE_NAME} ORDER BY ${ClubsContract.Columns.COUNTRY} AND ${ClubsContract.Columns.CLUB_TITLE}")
    suspend fun getAllClubs(): List<Clubs>

    //    получение клуба со турнирами. Через транзакцию для обеспечения атомарности
    @Transaction
    @Query("SELECT * FROM ${ClubsContract.TABLE_NAME} WHERE  ${ClubsContract.Columns.CLUB_TITLE} = :title AND  ${ClubsContract.Columns.CITY} = :city")
    suspend fun getClubWithTournament(title: String, city: String): ClubsWithTournaments?

    //    добавление нового клуба
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewClub(clubs: List<Clubs>)

    //    обновление клуба
    @Update
    suspend fun updateClub(club: Clubs)

    //    удаление клуба
    @Delete
    suspend fun deleteClub(club: Clubs)

    //    удаление всех клубов
    @Query("DELETE FROM ${ClubsContract.TABLE_NAME}")
    suspend fun deleteAllClubs()
}