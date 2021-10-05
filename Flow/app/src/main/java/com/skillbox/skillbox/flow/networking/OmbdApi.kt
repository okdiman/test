package com.skillbox.skillbox.flow.networking

import com.skillbox.skillbox.flow.classes.MovieType
import com.skillbox.skillbox.flow.classes.MoviesSearchResult
import com.skillbox.skillbox.flow.database.MovieEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface OmbdApi {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") title: String,
        @Query("type") type: MovieType?
    ): MoviesSearchResult?
}