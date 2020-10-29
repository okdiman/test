package com.skillbox.skillbox.homeworkmodule5

class Auto(
    val wheelCount: Int,
    val doorCount: Int,
    maxSpeed: Int
) : Vehicle(maxSpeed) {
    var isDoorOpen: Boolean = false
    fun openDoor() {
        if (!isDoorOpen) {
            println("Дверь открыта")
        }
        isDoorOpen = true
    }

    fun closeDoor() {
        if (isDoorOpen) {
            println("Дверь закрыта")
        }
        isDoorOpen = false
    }

    override fun accelerate(speed: Int) {
        if (isDoorOpen) {
            println("you cant accelerate with opened door")
        } else {
            super.accelerate(speed)
        }
    }

    override fun getTittle(): String = "auto"

    fun accelerate(speed: Int, force: Boolean) {
        if (force) {
            if (isDoorOpen) println("u accelerate with opened door")
            super.accelerate(speed)
        } else {
            accelerate(speed)
        }
    }
}
