package com.skillbox.multithreading.threading

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.multithreading.networking.Movie

class MovieViewModel() : ViewModel() {
    private val movieLiveData = MutableLiveData<List<Movie>>()
    val moviesLive: LiveData<List<Movie>>
        get() = movieLiveData

    private val repository = MovieRepository()

    private val moviesList = arrayListOf(
        "tt10541088",
        "tt1190634",
        "tt0068646",
        "tt0071562",
        "tt0110912",
        "tt0120737",
        "tt1375666",
        "tt0080684"
    )


    fun requestMovies() {
        repository.fetchMovies(moviesList) { movies ->
            val newMoviesList = movies + movieLiveData.value.orEmpty()
            Log.d("ewq", "$movies")
            movieLiveData.postValue(newMoviesList)
            Log.d("ewq", "${moviesLive.value}")
        }
    }
}