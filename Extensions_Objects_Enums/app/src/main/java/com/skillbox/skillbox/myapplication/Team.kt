package com.skillbox.skillbox.myapplication

import kotlin.random.Random

class Team() {
    var team = fillInTheList()
}

private fun fillInTheList(): Any {
    val newWarriors = emptyList<Warrior>().toMutableList()
    val numbersOfWarriors = readLine()?.toIntOrNull() ?: return "вы ввели не число"
    while (numbersOfWarriors > 0) {
        when (Random.nextInt(100 + 1)) {
            in 1..10 -> newWarriors.add(General())
            in 11..30 -> newWarriors.add(Captain())
            in 31..60 -> newWarriors.add(Soldier())
            in 61..100 -> newWarriors.add(Recruit())
        }
        numbersOfWarriors - 1
    }
    return newWarriors
}
