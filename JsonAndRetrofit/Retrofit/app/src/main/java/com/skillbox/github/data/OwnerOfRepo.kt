package com.skillbox.github.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnerOfRepo(
    val login: String
)