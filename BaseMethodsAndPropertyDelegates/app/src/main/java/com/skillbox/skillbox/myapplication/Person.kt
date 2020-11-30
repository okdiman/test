package com.skillbox.skillbox.myapplication

import kotlin.random.Random

class Person(
    val height: Int,
    val weight: Int,
    val name: String,
) {
    val pets = hashSetOf<Animal>()

    fun buyPet() {
        val animal = Animal(Random.nextInt(9 + 1), Random.nextInt(14 + 1), "Charly")
        pets.add(animal)
        println("Добавлено животное")
        return println("Список животных ${PrintNamePersonAndHisPets(this, pets)}")
    }

    override fun toString(): String {
        return "(name='$name', height=$height, weight=$weight)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (height != other.height) return false
        if (weight != other.weight) return false
        if (name != other.name) return false
        if (pets != other.pets) return false

        return true
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + weight
        result = 31 * result + name.hashCode()
        result = 31 * result + pets.hashCode()
        return result
    }


}