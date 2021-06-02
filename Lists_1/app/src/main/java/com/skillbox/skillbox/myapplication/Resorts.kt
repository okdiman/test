package com.skillbox.skillbox.myapplication

sealed class Resorts {
    data class Seas(
        val name: String,
        val country: String,
        val photo: Int,
        val sea: String
    ) : Resorts()

    data class Mountains(
        val name: String,
        val country: String,
        val photo: Int,
        val mountain: String
    ) : Resorts()

    data class Oceans(
        val name: String,
        val country: String,
        val photo: Int,
        val ocean: String
    ) : Resorts()
}