package com.skillbox.skillbox.networking.classes

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class MovieCustomAdapter {
    @FromJson
    fun fromJson(CustomMovie: CustomMovie): Movie {
        val scores = CustomMovie.scores.sourceList.associateBy({ it.source }, { it.value })
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
    data class ListSourceWrapper(
        val sourceList: List<Scores>
    )

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
        val scores: ListSourceWrapper,
        @Json(name = "Rated")
        val rating: Rating = Rating.GENERAL
    )

}