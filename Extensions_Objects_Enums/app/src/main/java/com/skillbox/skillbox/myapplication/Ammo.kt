package com.skillbox.skillbox.myapplication

import kotlin.random.Random

enum class Ammo(
    val damage: Int,
    val probabilityOfCrit: Int,
    val coeffCrit: Int
) {
    PISTOL(3, 40, 2),
    MACHINEGUN(7, 25, 3),
    SHOTGUN(15, 10, 4);

    fun currentDamage(): Int {
        val crit = Random.nextInt(100 + 1).toBoolean()
        val currentDamage: Int
        if (crit) {
            currentDamage = coeffCrit * this@Ammo.damage
        } else {
            currentDamage = this@Ammo.damage
        }
        return currentDamage
    }

    fun Int.toBoolean(): Boolean {
        return this < this@Ammo.probabilityOfCrit
    }
}