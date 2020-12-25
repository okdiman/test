package com.skillbox.skillbox.homework3

import kotlin.random.Random

fun main() {
    calculateRandomInt()
        .takeIf { it > 4 }
        ?.let { print("Вы ввели число больше 4") }
}

fun calculateRandomInt(): Int = Random.nextInt()
