package com.skillbox.skillbox.myapplication

import kotlin.random.Random

enum class Ammo(
    private val damage: Int,
    private val probabilityOfCrit: Int,
    private val coeffCrit: Int
) {
    PISTOL(3, 40, 2),
    MACHINEGUN(7, 25, 3),
    SHOTGUN(15, 10, 4);

    fun currentDamage(): Int {
        val currentDamage: Int
        if (this@Ammo.damage.isCritical()) {
            currentDamage = coeffCrit * this@Ammo.damage
        } else {
            currentDamage = this@Ammo.damage
        }
        return currentDamage
    }

    private fun Int.isCritical(): Boolean {
        return Random.nextInt(100 + 1) < this@Ammo.probabilityOfCrit
    }
}
