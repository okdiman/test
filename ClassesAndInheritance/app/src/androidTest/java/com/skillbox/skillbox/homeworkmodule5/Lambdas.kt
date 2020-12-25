package com.skillbox.skillbox.homeworkmodule5

import com.skillbox.skillbox.homework3.Car

fun main() {
    val lambda = { println("I love Ksusha") }
    lambda()

    val lambdaWihtParam = { x: Int -> println("X from lambda: $x") }
    lambdaWihtParam(32)

    val sumLambda = { x: Int, y: Int -> x + y }
    println("Sum x and y = ${sumLambda(21, 87)}")

    val users = listOf(
        User("Ksushka", "Okuneva", 25),
        User("Dimondus", "Okunev", 26),
        User("Katya", "Okuneva", 20),
        User("Denis", "Trishin", 24)
    )

    val oldestUser = users.maxByOrNull { user -> user.age }
    println(oldestUser)

    val maxNameUser = users.maxByOrNull { it.getFullnameLenght() }
    println(maxNameUser)

    val car = Car.default

    car.openDoor()
    car.closeDoor { println("close door custom") }

    makeCalculations {
        println("result = $it")
    }
}

inline fun makeCalculations(callback: (Int) -> Unit) {
    val value = 1 + 1
    callback(value)
}
