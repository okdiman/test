package com.skillbox.skillbox.networking.classes

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Scores(
    @Json(name = "Source")
    val source: String,
    @Json(name = "Value")
    val value: String
) : Parcelable