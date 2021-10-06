package com.skillbox.skillbox.flow.networking

import com.skillbox.skillbox.flow.classes.MovieType
import com.skillbox.skillbox.flow.classes.MoviesSearchResult
import com.skillbox.skillbox.flow.database.MovieContract
import com.skillbox.skillbox.flow.database.MovieEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
//    запрос на получение фильмов с сайта OMDbApi
    @GET("/")
    suspend fun searchMovies(
        @Query("s") title: String,
        @Query("type") type: MovieType?
    ): MoviesSearchResult?
}