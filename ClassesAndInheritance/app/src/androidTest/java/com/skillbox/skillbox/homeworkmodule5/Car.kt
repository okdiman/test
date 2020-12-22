package com.skillbox.skillbox.homework3

import com.skillbox.skillbox.homeworkmodule5.Engine
import com.skillbox.skillbox.homeworkmodule5.User

class Car constructor(
        val wheelCount: Int,
        val doorCount: Int,
        val bodyLenght: Int,
        val bodyWidht: Int,
        val bodyHeight: Int,
) {
    init {
        println("Создается объект")
    }

    private lateinit var driver: User

    operator fun component1(): Int = wheelCount
    operator fun component2(): Int = doorCount
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

    var isDoorOpen: Boolean = false

    private val engine: Engine by lazy {
        Engine()
    }

    fun openDoor(openCallBack: () -> Unit = { println("door is opened")}) {
        if (!isDoorOpen) {
            openCallBack()
        }
        isDoorOpen = true
    }

    fun closeDoor(closeCallback: () -> Unit = {println("door is closed")}) {
        if (isDoorOpen) {
            closeCallback()
//            if (::driver.isInitialized) {
//                println("driver is $driver")
//            }
        }
        isDoorOpen = false
    }


    fun setDriver(driver: User) {
        this.driver = driver
    }

    fun accelerate(speed: Int) {
        engine.use()
        val needFuel = speed / 3
        if (fuelCount > needFuel) {
            currentSpeed += speed
            useFuel(needFuel)
        } else {
            println("Недостаточно топлива")
        }
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Car

        if (wheelCount != other.wheelCount) return false
        if (doorCount != other.doorCount) return false
        if (bodyLenght != other.bodyLenght) return false
        if (bodyWidht != other.bodyWidht) return false
        if (bodyHeight != other.bodyHeight) return false
        if (fuelCount != other.fuelCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = wheelCount
        result = 31 * result + doorCount
        result = 31 * result + bodyLenght
        result = 31 * result + bodyWidht
        result = 31 * result + bodyHeight
        result = 31 * result + fuelCount
        return result
    }

    companion object {
        val default = Car(4,4,2000,1500,1000)

        fun createWithDefaultWheelCount(doorCount: Int, bodyLenght: Int, bodyWidht: Int, bodyHeight: Int): Car{
            return Car (4,doorCount = doorCount, bodyLenght = bodyLenght, bodyWidht = bodyWidht,bodyHeight=bodyHeight )
        }
    }
}