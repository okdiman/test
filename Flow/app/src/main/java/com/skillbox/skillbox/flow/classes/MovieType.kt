package com.skillbox.skillbox.flow.classes

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json


enum class MovieType {
    @SerializedName("movie")
    MOVIE,
    @SerializedName("series")
    SERIES,
    @SerializedName("episode")
    EPISODE
}