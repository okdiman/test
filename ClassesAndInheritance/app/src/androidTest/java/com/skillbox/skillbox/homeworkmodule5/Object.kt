package com.skillbox.skillbox.homeworkmodule5

import com.skillbox.skillbox.homework3.Car

fun main() {
    Cars.isuzu
    Cars.accelerate(50)

    Car.default
    Car.createWithDefaultWheelCount(4, 2000, 1800, 1200)

    val a = Cars
    val b = Car
}
private object Cars : Vehicle(120) {
    val isuzu = Car(4, 5, 4000, 2500, 2000)
    val vaz = Car(4, 5, 3000, 2000, 1500)

    fun someFun() {
    }
}
