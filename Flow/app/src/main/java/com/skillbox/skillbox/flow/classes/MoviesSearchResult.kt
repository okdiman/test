package com.skillbox.skillbox.flow.classes

import com.google.gson.annotations.SerializedName
import com.skillbox.skillbox.flow.database.MovieEntity

data class MoviesSearchResult(
    @SerializedName("Response")
    val response:Boolean,
    val totalResults:Int,
    @SerializedName("Search")
    val search:List<MovieEntity>?
)