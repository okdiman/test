package com.skillbox.skillbox.networking.classes

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "Title")
    val title: String,
    @Json(name = "Year")
    val year: String,
    @Json(name = "Genre")
    val genre: String = "",
    @Json(name = "Poster")
    val poster: String,
    @Json(name = "Ratings")
    val scores: MutableMap<String, String>,
    @Json(name = "Rated")
    val rating: Rating = Rating.GENERAL
) : Parcelable