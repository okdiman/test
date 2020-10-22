package com.skillbox.skillbox.homework1

import java.util.concurrent.atomic.LongAccumulator

fun main() {
    println("Введите число")
    val n = readLine()?.toIntOrNull() ?: return

    println("Вы ввели число: $n")

    println(calculateSumRec(n))
}

tailrec fun  calculateSumRec (n: Int, accum: Int = 0): Int {
    return if (n == 0) {
        accum
    } else {
        calculateSumRec(n - 1, accum + n)
    }
}

