package com.skillbox.github.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsersRepository(
    val id: Long,
    val name: String
)