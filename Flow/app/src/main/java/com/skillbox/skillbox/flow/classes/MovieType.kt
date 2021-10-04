package com.skillbox.skillbox.flow.classes

import com.squareup.moshi.Json


enum class MovieType {
    @Json(name = "movie")
    MOVIE,
    @Json(name = "series")
    SERIES,
    @Json(name = "episode")
    EPISODE
}