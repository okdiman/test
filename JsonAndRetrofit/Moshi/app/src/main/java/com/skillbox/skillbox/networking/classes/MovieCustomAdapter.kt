package com.skillbox.skillbox.networking.classes

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class MovieCustomAdapter {
    @FromJson
    fun fromJson(customMovie: customMovie): Movie {
        val scores = customMovie.scores.sourceList.associateBy({ it.source }, { it.value })
        return Movie(
            customMovie.title,
            customMovie.year,
            customMovie.genre,
            customMovie.poster,
            scores,
            customMovie.rating
        )
    }

    @JsonClass(generateAdapter = true)
    data class listSourceWrapper(
        val sourceList: List<Scores>
    )

    @JsonClass(generateAdapter = true)
    data class customMovie(
        @Json(name = "Title")
        val title: String,
        @Json(name = "Year")
        val year: String,
        @Json(name = "Genre")
        val genre: String = "",
        @Json(name = "Poster")
        val poster: String,
        @Json(name = "Ratings")
        val scores: listSourceWrapper,
        @Json(name = "Rated")
        val rating: Rating = Rating.GENERAL
    )

}