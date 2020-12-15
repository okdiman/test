package com.skillbox.skillbox.homeworkmodule5

import com.skillbox.skillbox.homework3.Car

fun main() {
    val porsche = Auto(4, 2, 250)
    with(porsche) {
        refuel(100)
        openDoor()
        accelerate(150)
        accelerate(150, true)
        decelerate(80)
    }
    println("porsce is auto = ${porsche is Auto}")

    var vehicle = porsche

    val newCar: Auto? = vehicle as? Auto
    newCar?.closeDoor()

    val nissan: Vehicle = Auto(4, 2, 180)
    println(nissan.getTittle())

    listOf<Vehicle>(
        Vehicle(120),
        Auto(4,4,180)
    ).forEach {
        println(it.getTittle())
    }

    val shape = Rectangle(2,2,30,20)
    val largeShape = Rectangle (1, 2 , 50, 60)

    println("shape >= largeShape = ${shape>=largeShape}")

    val set: MutableSet<Rectangle> = sortedSetOf(
        largeShape,
        shape
    )
    println(set)

    val anonim = object: Shape {

        val add: Int = 123

        fun addish () = 8465

        override val name: String = "anonim shape"

        override fun calculateArea(): Double = 0.0

    }
    anonim.add
}