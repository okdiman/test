package com.skillbox.skillbox.homework3

fun main ()  {
    println("Введите число")
    val n = readLine()?.toIntOrNull() ?: return println("Incorrect")
    if (n < 0) {
        println("Ошибка. Введите положительное число")
    } else {
        println("Введите $n номера (ов) пользователей")

        print(numbers(n))
    }
}

fun numbers(n: Int): MutableList<String> {
    var currentNumber = 1
    val listOfNumbers = mutableListOf<String>()
    while (currentNumber <= n) {
        ++currentNumber
        val numbersForList = readLine()?.toIntOrNull() ?: return listOfNumbers
        listOfNumbers.add("$numbersForList")
    }
    return  listOfNumbers
}