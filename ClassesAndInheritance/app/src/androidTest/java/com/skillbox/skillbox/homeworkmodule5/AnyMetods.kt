package com.skillbox.skillbox.homeworkmodule5

import com.skillbox.skillbox.homework3.Car

fun main() {
    val car1 = Car(4, 2, 2000, 1500, 1000)
    val car2 = Car(4, 2, 2000, 1500, 1000)
    val car3 = car1

    println("car equals to car2 by reference ${car1 === car2}")
    println("car equals to car3 by reference ${car1 === car3}")
    println("car equals to car2 by value ${car1 == car2}")

    val cars = listOf(
        car1,
        Car(3, 2, 2000, 1500, 1000),
        Car(4, 2, 2000, 1500, 1000)
    )

    println(cars.contains(Car(3, 2, 2000, 1500, 1000)))

    val map = hashMapOf(
        car1 to "1",
        car2 to "2",
        Car(3, 2, 2000, 1500, 1000) to "3"
    )

    println(map[Car(4, 2, 2000, 1500, 1000)])
}
