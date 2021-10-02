package com.skillbox.skillbox.flow.fragments

import android.util.Log
import com.skillbox.skillbox.flow.classes.MovieType
import com.skillbox.skillbox.flow.classes.MyMovies
import com.skillbox.skillbox.flow.networking.Network

class MainFragmentRepository {
    suspend fun searchMovie(query: Pair<String, MovieType>) {
        val req = Network.api.searchMovies("http://www.omdbapi.com/", query.first, query.second)
        Log.i("req", "$req")
    }
}