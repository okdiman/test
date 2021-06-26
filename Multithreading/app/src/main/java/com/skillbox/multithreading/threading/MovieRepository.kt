package com.skillbox.multithreading.threading

import com.skillbox.multithreading.networking.Movie

class MovieRepository {

    fun addMovie(title: String, year: Int): Movie {
        return Movie(title, year)
    }
}