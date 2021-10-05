package com.skillbox.skillbox.flow.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skillbox.skillbox.flow.classes.MovieType
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query(
        "SELECT * FROM ${MovieContract.TABLE_NAME} WHERE ${MovieContract.Columns.TITLE} = :title " +
                "AND ${MovieContract.Columns.TYPE} = :type ORDER BY ${MovieContract.Columns.YEAR} DESC"
    )
    suspend fun getMovies(title: String, type: MovieType): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewFilms(movies: List<MovieEntity>)

    @Query("DELETE FROM ${MovieContract.TABLE_NAME}")
    suspend fun deleteAll()
}