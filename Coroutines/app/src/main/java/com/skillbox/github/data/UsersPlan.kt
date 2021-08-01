package com.skillbox.github.data

import com.squareup.moshi.JsonClass

//класс исплользуемого пользователем плана
@JsonClass(generateAdapter = true)
data class UsersPlan(
    val name: String,
    val space: Int,
    val private_repos: Int,
    val collaborators: Int
)