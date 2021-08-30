package com.skillbox.skillbox.roomdao.database.dao

import androidx.room.*
import com.skillbox.skillbox.roomdao.database.connections.StadiumWithClubs
import com.skillbox.skillbox.roomdao.database.connections.StadiumsWithAttendance
import com.skillbox.skillbox.roomdao.database.contracts.StadiumsContract
import com.skillbox.skillbox.roomdao.database.entities.Stadiums

@Dao
interface StadiumsDao {
    @Query("SELECT * FROM ${StadiumsContract.TABLE_NAME}")
    suspend fun getAllStadiums(): List<Stadiums>

    @Transaction
    @Query("SELECT * FROM ${StadiumsContract.TABLE_NAME} WHERE  ${StadiumsContract.Columns.ID} = :stadiumId")
    suspend fun getStadiumsWithClubs(stadiumId: Long): StadiumWithClubs?

    @Transaction
    @Query("SELECT * FROM ${StadiumsContract.TABLE_NAME} WHERE  ${StadiumsContract.Columns.ID} = :stadiumId")
    suspend fun getStadiumsWithAttendance(stadiumId: Long): StadiumsWithAttendance?


    @Query("SELECT * FROM ${StadiumsContract.TABLE_NAME} WHERE  ${StadiumsContract.Columns.ID} = :stadiumId")
    suspend fun getStadiumById(stadiumId: Long): Stadiums


    @Query("SELECT * FROM ${StadiumsContract.TABLE_NAME} WHERE  ${StadiumsContract.Columns.STADIUM_NAME} = :stadiumName")
    suspend fun getStadiumByName(stadiumName: String): Stadiums

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewStadium(stadiums: List<Stadiums>)

    @Update
    suspend fun updateStadiums(stadiums: Stadiums)

    @Delete
    suspend fun deleteStadiums(stadiums: Stadiums)

    @Query("DELETE FROM ${StadiumsContract.TABLE_NAME}")
    suspend fun deleteAllStadiums()
}