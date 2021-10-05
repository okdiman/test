package com.skillbox.skillbox.flow.classes

import com.google.gson.annotations.SerializedName

enum class MovieType {
//    аннотации SerializedName для преобразования ответа сервера из Gson формата
    @SerializedName("movie")
    MOVIE,
    @SerializedName("series")
    SERIES,
    @SerializedName("episode")
    EPISODE
}