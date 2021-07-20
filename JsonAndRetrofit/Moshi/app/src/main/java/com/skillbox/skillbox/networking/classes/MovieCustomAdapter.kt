package com.skillbox.skillbox.networking.classes

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class MovieCustomAdapter {
    @FromJson
    fun fromJson(CustomMovie: CustomMovie): Movie {
        val scores = CustomMovie.scores.associateBy({ it.source }, { it.value }).toMutableMap()
        return Movie(
            CustomMovie.title,
            CustomMovie.year,
            CustomMovie.genre,
            CustomMovie.poster,
            scores,
            CustomMovie.rating
        )
    }

    @JsonClass(generateAdapter = true)
    data class CustomMovie(
        @Json(name = "Title")
        val title: String,
        @Json(name = "Year")
        val year: String,
        @Json(name = "Genre")
        val genre: String = "",
        @Json(name = "Poster")
        val poster: String,
        @Json(name = "Ratings")
        val scores: List<Scores>,
        @Json(name = "Rated")
        val rating: Rating = Rating.GENERAL
    )

}