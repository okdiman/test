package com.skillbox.multithreading.threading

import android.util.Log
import com.skillbox.multithreading.networking.Movie
import com.skillbox.multithreading.networking.Network
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MovieRepository {

    private fun getMovieById(id: String): Movie? {
        return Network.getMovieById(id)
    }

    fun fetchMovies(
        listOfMoviesId: List<String>,
        onMoviesFetched: (movies: MutableList<Movie>) -> Unit
    ) {
        Thread {
            val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
            val allMovies = Collections.synchronizedList(mutableListOf<Movie>())
            val startTime = System.currentTimeMillis()
            listOfMoviesId.chunked(1).forEach { movieId ->
                executor.execute() {
                    val movie = getMovieById(movieId[0])
                    if (movie != null) {
                        val movieForList = Movie(movie.title, movie.year)
                        allMovies.add(movieForList)
                    }
                }
            }
            executor.shutdown()
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)
            onMoviesFetched(allMovies)
            val endTime = System.currentTimeMillis() - startTime
            Log.d("timing", "$endTime")
        }.run()


//        Thread {
//            val startTime = System.currentTimeMillis()
//            val allMovies = Collections.synchronizedList(mutableListOf<Movie>())
//            val threads = listOfMoviesId.chunked(1).map { movieId ->
//                Thread {
//                    val movie = getMovieById(movieId[0])
//                    if (movie != null) {
//                        val movieForList = Movie(movie.title, movie.year)
//                        allMovies.add(movieForList)
//                    }
//                }
//            }
//            threads.forEach { it.start() }
//            threads.forEach { it.join() }
//            onMoviesFetched(allMovies)
//            val endTime = System.currentTimeMillis() - startTime
//            Log.d("timing", "$endTime")
//        }.start()

    }
}