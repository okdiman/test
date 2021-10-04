package com.skillbox.skillbox.flow.fragments

import android.util.Log
import com.skillbox.skillbox.flow.classes.MovieType

class MainFragmentRepository {
    suspend fun searchMovie(query: Pair<String, MovieType>) {
        Log.i("query", "$query")
//        val req = "http://www.omdbapi.com/"
//        Log.i("req", "${Network.api.searchMovies(req, query.first, query.second)}")
    }
}