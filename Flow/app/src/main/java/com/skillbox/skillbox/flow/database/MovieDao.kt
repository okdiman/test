package com.skillbox.skillbox.flow.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skillbox.skillbox.flow.classes.MovieType

@Dao
interface MovieDao {
    @Query(
        "SELECT * FROM ${MovieContract.TABLE_NAME} WHERE ${MovieContract.Columns.TITLE} = :title " +
                "AND ${MovieContract.Columns.TYPE} = :type ORDER BY ${MovieContract.Columns.TITLE}"
    )
    suspend fun observeMovies(title: String, type: MovieType?): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewFilms(movies: List<MovieEntity>)
}