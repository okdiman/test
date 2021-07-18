package com.skillbox.skillbox.networking.classes

import android.os.Parcelable
import com.google.gson.annotations.JsonAdapter
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class Movie(
    val title: String,
    val year: String,
    val type: String,
    val id: String,
    val poster: String
)