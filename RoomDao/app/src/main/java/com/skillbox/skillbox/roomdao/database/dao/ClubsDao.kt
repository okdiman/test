package com.skillbox.skillbox.roomdao.database.dao

import androidx.room.*
import com.skillbox.skillbox.roomdao.database.connections.ClubsWithStadium
import com.skillbox.skillbox.roomdao.database.connections.ClubsWithTournaments
import com.skillbox.skillbox.roomdao.database.contracts.ClubsContract
import com.skillbox.skillbox.roomdao.database.entities.Clubs

@Dao
interface ClubsDao {

    @Query("SELECT * FROM ${ClubsContract.TABLE_NAME}")
    suspend fun getAllClubs(): List<Clubs>

    @Transaction
    @Query("SELECT * FROM ${ClubsContract.TABLE_NAME} WHERE  ${ClubsContract.Columns.TITLE} = :title AND  ${ClubsContract.Columns.CITY} = :city")
    suspend fun getClubWithStadium(title: String, city: String): ClubsWithStadium

    @Transaction
    @Query("SELECT * FROM ${ClubsContract.TABLE_NAME} WHERE  ${ClubsContract.Columns.TITLE} = :title AND  ${ClubsContract.Columns.CITY} = :city")
    suspend fun getClubWithTournament(title: String, city: String): ClubsWithTournaments

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewClub(clubs: List<Clubs>)

    @Update
    suspend fun updateClub(club: Clubs)

    @Delete
    suspend fun deleteClub(club: Clubs)

    @Query("DELETE FROM ${ClubsContract.TABLE_NAME}")
    suspend fun deleteAllClubs()

}