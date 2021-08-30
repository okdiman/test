package com.skillbox.skillbox.roomdao.database.dao

import androidx.room.*
import com.skillbox.skillbox.roomdao.database.connections.ClubsWithStadium
import com.skillbox.skillbox.roomdao.database.connections.ClubsWithTournaments
import com.skillbox.skillbox.roomdao.database.contracts.ClubsContract
import com.skillbox.skillbox.roomdao.database.entities.Clubs

@Dao
interface ClubsDao {

    //    получение списка всех клубов
    @Query("SELECT * FROM ${ClubsContract.TABLE_NAME}")
    suspend fun getAllClubs(): List<Clubs>

    //    получение клуба со стадионом. Через транзакцию для обеспечения атомарности
    @Transaction
    @Query("SELECT * FROM ${ClubsContract.TABLE_NAME} WHERE  ${ClubsContract.Columns.CLUB_TITLE} = :title AND  ${ClubsContract.Columns.CITY} = :city")
    suspend fun getClubWithStadium(title: String, city: String): ClubsWithStadium?

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