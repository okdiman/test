package com.skillbox.skillbox.flow.fragments

import android.util.Log
import com.skillbox.skillbox.flow.classes.MovieType
import com.skillbox.skillbox.flow.database.Database
import com.skillbox.skillbox.flow.database.MovieEntity
import com.skillbox.skillbox.flow.networking.Network
import kotlinx.coroutines.flow.Flow

class GeneralRepository {

    //    создаем инстанс movieDao
    private val moviesDao = Database.instance.movieDao()

    //    поиск фильмов
    suspend fun searchMovie(query: Pair<String, MovieType>): List<MovieEntity> {
        Log.i("query", "$query")
//        создаем список, чтобы класть туда все полученные фильмы
        val listOfMovies = mutableListOf<MovieEntity>()
        return try {
//            делаем запрос в сеть для получения фильмов
            Network.api.searchMovies(query.first, query.second).apply {
//                добавляем каждый фильм в список
                this?.search?.forEach { movie ->
                    listOfMovies.add(movie)
                }
//                добавляем каждый фильм в БД
                moviesDao.addNewFilms(listOfMovies)
            }
//            возвращаем списко фильмов
            listOfMovies
        } catch (t: Throwable) {
//            при получении ошибки, возвращаем список фильмов из БД
            Log.i("errorInfo", "$t")
            moviesDao.getMovies(query.first, query.second)
        }
    }

    //    автообноление списка фильмов из БД
    fun observeMovies(): Flow<List<MovieEntity>> =
        moviesDao.observeMovies()
}