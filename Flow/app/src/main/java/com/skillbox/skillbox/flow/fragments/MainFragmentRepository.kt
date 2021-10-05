package com.skillbox.skillbox.flow.fragments

import android.util.Log
import com.skillbox.skillbox.flow.classes.MovieType
import com.skillbox.skillbox.flow.database.Database
import com.skillbox.skillbox.flow.database.MovieEntity
import com.skillbox.skillbox.flow.networking.Network

class MainFragmentRepository {
    private val moviesDao = Database.instance.movieDao()
    suspend fun searchMovie(query: Pair<String, MovieType>) {
        Log.i("query", "$query")
        val req = "http://www.omdbapi.com/"
        Log.i("req", "${Network.api.searchMovies(query.first, query.second)}")
    }

    suspend fun searchMoviesFromDatabase(query: Pair<String, MovieType>): List<MovieEntity> {
        return moviesDao.observeMovies(query.first, query.second)
    }
}