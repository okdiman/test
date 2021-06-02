package com.skillbox.skillbox.myapplication

sealed class Resorts {
    data class seas(
        val name: String,
        val country: String,
        val photo: Int,
        val sea: String
    ) : Resorts()

    data class mountains(
        val name: String,
        val country: String,
        val photo: Int,
        val mountain: String
    ) : Resorts()

    data class oceans(
        val name: String,
        val country: String,
        val photo: Int,
        val ocean: String
    ) : Resorts()
}