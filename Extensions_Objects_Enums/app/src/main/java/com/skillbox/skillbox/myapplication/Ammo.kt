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

    fun currentDamage() {
        val crit = Random.nextInt(100 + 1).toBoolean()
        val currentDamage = if (crit) {
            coeffCrit * damage
        } else {
            damage
        }
    }

    fun Int.toBoolean(): Boolean {
        return this < this@Ammo.probabilityOfCrit
    }
}