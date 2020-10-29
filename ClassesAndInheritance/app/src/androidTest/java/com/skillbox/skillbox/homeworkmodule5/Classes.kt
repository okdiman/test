package com.skillbox.skillbox.homework3

fun main (){
    val teslaCar = Car(
            wheelCount = 4, doorCount = 2, bodyLenght = 500, bodyWidht = 200, bodyHeight = 150
    )
    println("Tesla has ${teslaCar.wheelCount} wheels")


    teslaCar.accelerate(180)
    println("${teslaCar.currentSpeed}")

    teslaCar.decelerate(50)
    println("${teslaCar.currentSpeed}")

    // println("${teslaCar.fuelCount}")

}

