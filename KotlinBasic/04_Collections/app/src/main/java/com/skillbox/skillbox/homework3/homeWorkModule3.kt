package com.skillbox.skillbox.homework3

fun main() {
    println("Введите число")
    val n = readLine()?.toIntOrNull() ?: return println("Вы ввели не число")
    if (n < 0) {
        return println("Введите положительное число")
    } else {
        println("Вы ввели число: $n, введите еще $n")
        val b = (introductionOfNumbers(n))

        println("Введите число для поиска НОД")
        val a = readLine()?.toIntOrNull() ?: return println("Вы ввели не число")
        print(NODRec(a, b))
    }
}

fun introductionOfNumbers(n: Int): Int {
    var currentNOfNumber = 1
    var positiveNumbers = 0
    var sum = 0
    while (currentNOfNumber <= n) {
        ++currentNOfNumber
        println("Введите число ")
        val m = readLine()?.toIntOrNull() ?: return sum
        sum += m
        if (m > 0) {
            ++positiveNumbers
        }
    }
    println("Количество положительных чисел = $positiveNumbers, их сумма = $sum")
    return sum
}

tailrec fun NODRec(a: Int, b: Int): Any {
    return if (b == 0 || a == 0) {
        val Nod = a + b
        return "НОД вашего числа с суммой = $Nod"
    } else if (a > b) {
        NODRec(a = Math.abs(a) % Math.abs(b), b = Math.abs(b))
    } else {
        NODRec(b = Math.abs(b) % Math.abs(a), a = Math.abs(a))
    }
}
