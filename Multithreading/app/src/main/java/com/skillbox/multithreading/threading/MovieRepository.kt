package com.skillbox.multithreading.threading

import com.skillbox.multithreading.networking.Movie
import com.skillbox.multithreading.networking.Network
import java.util.*

class MovieRepository {

    private fun getMovieById(id: String): Movie? {
        return Network.getMovieById(id)
    }

    fun fetchMovies(
        listOfMoviesId: List<String>,
        onMoviesFetched: (movies: MutableList<Movie>) -> Unit
    ) {
        Thread {
            val allMovies = Collections.synchronizedList(mutableListOf<Movie>())
            val threads = listOfMoviesId.chunked(1).map { movieId ->
                Thread {
                    val movie = getMovieById(movieId[0])
                    if (movie != null) {
                        val movieForList = Movie(movie.title, movie.year)
                        allMovies.add(movieForList)
                    }
                }
            }
            threads.forEach { it.start() }
            threads.forEach { it.join() }
            onMoviesFetched(allMovies)
        }.start()
    }
}