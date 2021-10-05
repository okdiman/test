package com.skillbox.skillbox.flow.fragments

import android.util.Log
import com.skillbox.skillbox.flow.classes.MovieType
import com.skillbox.skillbox.flow.database.Database
import com.skillbox.skillbox.flow.database.MovieEntity
import com.skillbox.skillbox.flow.networking.Network

class MainFragmentRepository {
    private val moviesDao = Database.instance.movieDao()
    suspend fun searchMovie(query: Pair<String, MovieType>): List<MovieEntity> {
        Log.i("query", "$query")
        val listOfMovies = mutableListOf<MovieEntity>()
        try {
            Network.api.searchMovies(query.first, query.second).apply {
                this?.search?.forEach { movie ->
                    listOfMovies.add(movie)
                }
                moviesDao.addNewFilms(listOfMovies)
            }
            return listOfMovies
        } catch (t: Throwable) {
            return searchMoviesFromDatabase(query)
        }

    }

    private suspend fun searchMoviesFromDatabase(query: Pair<String, MovieType>): List<MovieEntity> {
        Log.i("observe", "$query")
        Log.i("observe", "${moviesDao.getMovies(query.first, query.second)}")
        return moviesDao.getMovies(query.first, query.second)
    }
}