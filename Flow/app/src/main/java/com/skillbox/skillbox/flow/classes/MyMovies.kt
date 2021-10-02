package com.skillbox.skillbox.flow.classes

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MyMovies(
    val title: String,
    val year: String,
    val runtime: String,
    val genre: String,
    val type: MovieType
) : Parcelable