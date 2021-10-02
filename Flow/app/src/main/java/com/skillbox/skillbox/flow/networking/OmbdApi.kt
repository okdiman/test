package com.skillbox.skillbox.flow.networking

import com.skillbox.skillbox.flow.classes.MovieType
import com.skillbox.skillbox.flow.classes.MyMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface OmbdApi {
    @GET
    suspend fun searchMovies(
        @Url url: String,
        @Query("s") title: String,
        @Query("type") type: MovieType?
    ): Call<List<MyMovies>>
}