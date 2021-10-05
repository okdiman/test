package com.skillbox.skillbox.flow.classes

import com.google.gson.annotations.SerializedName
import com.skillbox.skillbox.flow.database.MovieEntity

//обертка для полченного ответа от сервера OMDbAPI
data class MoviesSearchResult(
    //    аннотации SerializedName для преобразования ответа сервера из Gson формата
    @SerializedName("Response")
    val response: Boolean,
    val totalResults: Int,
    @SerializedName("Search")
    val search: List<MovieEntity>?
)