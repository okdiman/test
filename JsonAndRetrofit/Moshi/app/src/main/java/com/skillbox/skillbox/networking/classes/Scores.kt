package com.skillbox.skillbox.networking.classes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Scores(
    @Json(name = "Source")
    val source: String,
    @Json(name = "Value")
    val value: String
)