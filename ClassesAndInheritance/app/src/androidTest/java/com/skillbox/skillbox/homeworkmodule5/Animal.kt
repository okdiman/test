package com.skillbox.skillbox.homeworkmodule5

import kotlin.random.Random

open class Animal(
        maxAge: Int,
        var name: String
) : AgedAnimal(maxAge), Soundable {
    var age: Int = 0
        private set

    var energy: Int = Random.nextInt(10) + 1
        private set

    var weight: Int = Random.nextInt(5) + 1
        private set


    val isTooOld: Boolean
        get() = age >= this@Animal.maxAge


    fun sleep() {
        if (isTooOld) {
            return
        } else {
            energy + 5
            age++
            println("$name is sleeping")
        }
    }

    fun eat() {
        if (isTooOld) {
            return
        } else {
            energy + 3
            weight + 1
            println("$name is eating")
        }
    }

    private fun incrementAgeSometimes() {
        if (Random.nextBoolean())
            age++
    }

    open fun move() {
        if (isTooOld || energy <= 5 || weight <= 1) {
            return
        } else {
            energy - 5
            weight - 1
            incrementAgeSometimes()
            println("$name is moving")
        }
    }

    open fun makeChild(): Animal {
        energy = Random.nextInt(10) + 1
        weight = Random.nextInt(5) + 1
        age = 0
        val animal = Animal(this@Animal.maxAge, this.name)
        return animal
    }

    override fun toString(): String = "animal"
    override fun makeSound(): String = "ругается на лесном"
}