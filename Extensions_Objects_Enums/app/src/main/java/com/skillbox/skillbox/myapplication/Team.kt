package com.skillbox.skillbox.myapplication

import kotlin.random.Random

class Team() {
    var team = fillInTheList()
    var sumHealth: Int = 0

    fun sumHealth(): Int {
        team.forEach() {
            sumHealth + it.currentHealthLevel
        }
        return sumHealth
    }

    fun fillInTheList(): MutableList<AbstractWarrior> {
        val newWarriors = emptyList<AbstractWarrior>().toMutableList()
        var numbersOfWarriors =
            readLine()?.toIntOrNull() ?: return emptyList<AbstractWarrior>().toMutableList()
        while (numbersOfWarriors > 0) {
            when (Random.nextInt(100 + 1)) {
                in 1..10 -> newWarriors.add(General())
                in 11..30 -> newWarriors.add(Captain())
                in 31..60 -> newWarriors.add(Soldier())
                in 61..100 -> newWarriors.add(Recruit())
            }
            --numbersOfWarriors
        }
        println("Список войнов команды: $newWarriors")
        return newWarriors
    }
}

