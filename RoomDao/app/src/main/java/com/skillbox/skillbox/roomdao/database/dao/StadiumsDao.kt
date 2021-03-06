package com.skillbox.skillbox.roomdao.database.dao

import androidx.room.*
import com.skillbox.skillbox.roomdao.database.connections.StadiumsWithAttendance
import com.skillbox.skillbox.roomdao.database.contracts.StadiumsContract
import com.skillbox.skillbox.roomdao.database.entities.Stadiums

@Dao
interface StadiumsDao {

    //    получение списка всех стадионов сортированный по названию
    @Query("SELECT DISTINCT * FROM ${StadiumsContract.TABLE_NAME} ORDER BY ${StadiumsContract.Columns.STADIUM_NAME}")
    suspend fun getAllStadiums(): List<Stadiums>

    //    получение стадиона и посещаемости. Через транзакцию для атомарности
    @Transaction
    @Query("SELECT * FROM ${StadiumsContract.TABLE_NAME} WHERE  ${StadiumsContract.Columns.STADIUM_NAME} = :stadiumName")
    suspend fun getStadiumsWithAttendance(stadiumName: String): StadiumsWithAttendance?

    //    получение стадиона по ID
    @Query("SELECT * FROM ${StadiumsContract.TABLE_NAME} WHERE  ${StadiumsContract.Columns.ID} = :stadiumId")
    suspend fun getStadiumById(stadiumId: Long): Stadiums

    //    получение стадиона по имени
    @Query("SELECT * FROM ${StadiumsContract.TABLE_NAME} WHERE  ${StadiumsContract.Columns.STADIUM_NAME} = :stadiumName")
    suspend fun getStadiumByName(stadiumName: String): Stadiums

    //    добавление стадиона
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewStadium(stadiums: List<Stadiums>)

    //    удаление стадиона
    @Delete
    suspend fun deleteStadiums(stadiums: Stadiums)
}