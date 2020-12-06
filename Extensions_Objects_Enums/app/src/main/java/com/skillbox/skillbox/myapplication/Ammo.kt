package com.skillbox.skillbox.myapplication

import kotlin.random.Random

enum class Ammo(
    val damage: Int,
    val probabilityOfCrit: Int,
    val coeffCrit: Int
) {
    PISTOL(10, 40, 2),
    MACHINEGUN(20, 25, 3),
    SHOTGUN(40, 10, 5);

    fun currentDamage(): Int {
        val crit = Random.nextInt(100 + 1).toBoolean()
        val currentDamage: Int
        if (crit) {
            currentDamage = coeffCrit * this@Ammo.damage
        } else {
            currentDamage = this@Ammo.damage
        }
        println("нанесен урон за выстрел $currentDamage")
        return currentDamage
    }

    fun Int.toBoolean(): Boolean {
        return this < this@Ammo.probabilityOfCrit
    }
}