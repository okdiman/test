package com.skillbox.skillbox.flow.classes

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MyMovies(
    @Json(name = "Title")
    val title: String,
    @Json(name = "Year")
    val year: String,
    @Json(name = "Runtime")
    val runtime: String,
    @Json(name = "Genre")
    val genre: String,
    @Json(name = "Poster")
    val poster: String?,
    @Json(name = "Type")
    val type: MovieType
) : Parcelable