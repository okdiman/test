package com.skillbox.skillbox.flow.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skillbox.skillbox.flow.classes.MovieType

@Dao
interface MovieDao {

    //    запрос на получение из БД всех фильмов в соотвествии с условием
    @Query(
        "SELECT * FROM ${MovieContract.TABLE_NAME} WHERE ${MovieContract.Columns.TITLE} " +
                "LIKE '%' || :title || '%' AND ${MovieContract.Columns.TYPE} = :type " +
                "ORDER BY ${MovieContract.Columns.YEAR} DESC"
    )
    suspend fun getMovies(title: String, type: MovieType): List<MovieEntity>

    //    добавление новых фильмов в БД
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewFilms(movies: List<MovieEntity>)

    //    удаление всех фильмов из БД
    @Query("DELETE FROM ${MovieContract.TABLE_NAME}")
    suspend fun deleteAll()
}