package com.skillbox.multithreading.threading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.multithreading.networking.Movie

class MovieViewModel() : ViewModel() {
    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = movieLiveData

    private val repository = MovieRepository()

    fun addMovie(title: String, year: Int) {

        val newMovie = repository.addMovie(title, year)

        val updatedMovieList = arrayListOf(newMovie) + movieLiveData.value.orEmpty()

        movieLiveData.postValue(updatedMovieList)
    }
}