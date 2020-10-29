package com.skillbox.skillbox.homeworkmodule5

open class Vehicle (
    val maxSpeed: Int
) {
    init {
        println("Создается объект")
    }

    var currentSpeed = 0
        get() {
            println("currentSpeed get")
            return field
        }
        private set(value) {
            println("currentSpeed set $value")
            field = value
        }

    var fuelCount: Int = 0
        private set

    val isStopped: Boolean
        get() = currentSpeed == 0

    open fun accelerate(speed: Int) {
        val needFuel = speed / 3
        if (fuelCount > needFuel) {
            currentSpeed += speed
            useFuel(needFuel)
        } else {
            println("Недостаточно топлива")
        }
    }

    open fun getTittle (): String {
        return "vehicle"
    }

    fun decelerate(speed: Int) {
        currentSpeed = maxOf(0, currentSpeed - speed)
    }

    private fun useFuel(fuelCount: Int) {
        this.fuelCount -= fuelCount
    }

    fun refuel(fuelCount: Int) {
        if (currentSpeed == 0) {
            this.fuelCount += fuelCount
        } else {
            println("Остановите машину")
        }
    }
}