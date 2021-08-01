package com.skillbox.github.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Followings (
    val login: String,
    val id: Int
)