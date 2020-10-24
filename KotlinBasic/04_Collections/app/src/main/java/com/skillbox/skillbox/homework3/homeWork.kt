package com.skillbox.skillbox.homework3

fun main() {
    println("Введите число")
    val n = readLine()?.toIntOrNull() ?: return println("Incorrect")
    if (n < 0) {
        println("Ошибка. Введите положительное число")
    } else {
        println("Введите $n номера (ов) пользователей")

        val listOfNumbers = phoneBook(n)
        println(listOfNumbers.filter { it.startsWith("+7") })

        val setOfNumbers = listOfNumbers.toSet()
        println(setOfNumbers.size)
        println(listOfNumbers.sumBy { it.length })

        val mutableMap = emptyMap<String, String>().toMutableMap()
        var m = 0
        while (setOfNumbers.size > m) {
            println("Введите имя человека с номером телефона ${setOfNumbers.toList()[m]}:")
            val name = readLine()
            mutableMap.put(setOfNumbers.toList()[m], "$name")
            ++m
        }
        println(mutableMap)
        var q = 0
        while (setOfNumbers.size > q) {
            val p = setOfNumbers.toList()[q]
            println("Человек: ${mutableMap[p]}, номер телефона: ${mutableMap.mapValues { p }[p]}")
            ++q
        }

    }
}

fun phoneBook(n: Int): MutableList<String> {
    var serialNumber = 1
    val listOfNumbers = mutableListOf<String>()
    while (serialNumber <= n) {
        ++serialNumber
        val numbersForList = readLine()
        listOfNumbers.add("$numbersForList")
    }
    println(listOfNumbers)
    return listOfNumbers
}