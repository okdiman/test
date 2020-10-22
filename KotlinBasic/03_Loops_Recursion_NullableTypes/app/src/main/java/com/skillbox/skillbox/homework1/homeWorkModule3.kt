package com.skillbox.skillbox.homework1


fun main() {
    println("Введите число")
    val n = readLine()?.toIntOrNull() ?: return println("Вы ввели не число")
    if (n < 0) {
        return println("Введите положительное число")
    } else {
        println("Вы ввели число: $n, введите еще $n")
        print(introductionOfNumbers(n))

        println("Введите число для поиска НОД")
        val a = readLine()?.toIntOrNull() ?: return println("Вы ввели не число")
        println("Введите число из пункта 5")
        val b = readLine()?.toIntOrNull() ?: return println("Вы ввели не число")
        print(NODRec(a, b))
    }
}

fun introductionOfNumbers(n: Int): Any {
    var currentNOfNumber = 1
    var positiveNumbers = 0
    var sum = 0
    while (currentNOfNumber <= n) {
        ++currentNOfNumber
        println("Введите число ")
        val m = readLine()?.toIntOrNull() ?: return "Вы ввели не число"
        sum += m
        if (m > 0) {
            ++positiveNumbers
        }
    }
    return println("Количество положительных чисел = $positiveNumbers, их сумма = $sum")
}

tailrec fun NODRec(a: Int, b: Int): Any {
    return if (b == 0 || a == 0) {
        return "НОД = ${Math.abs(a)}"
    } else if (a > b) {
        NODRec(a = a % b, b = b)
    } else {
        NODRec(b = b % a, a = a)
    }
}

