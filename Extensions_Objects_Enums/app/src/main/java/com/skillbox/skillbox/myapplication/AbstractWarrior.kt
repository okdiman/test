package com.skillbox.skillbox.myapplication

import kotlin.random.Random

abstract class AbstractWarrior(
    val maxHealthLevel: Int,
    override val chanceToDodge: Int,
    val hitProbability: Int,
    val weapon: AbstractWeapon
) : Warrior {
    var currentHealthLevel = maxHealthLevel
    var currentDamage: Int = Ammo.MACHINEGUN.currentDamage()
    var sumCurrentDamage: Int = 0

    var hit: Boolean = true

    fun death() {
        if (currentHealthLevel < 1) {
            isKilled = true
        }
        println("$this убит")
    }


    override fun attack(warrior: Warrior) {
        if (weapon.availabilityOfAmmo) {
            weapon.recharge()
            return
        } else {
            println("$this стреляет по $warrior")
            weapon.receivingOfAmmo()
            sumCurrentDamage = 0
            weapon.receivingAmmos.forEach() {
                hit = when {
                    Random.nextInt(100 + 1) < this.hitProbability && Random.nextInt(100 + 1) > warrior.chanceToDodge -> true
                    else -> false
                }
                if (hit) {
                    sumCurrentDamage += currentDamage
                }
            }
            println("$this наносит $warrior $sumCurrentDamage урона")
            warrior.takeDamage(sumCurrentDamage)
        }
    }

    override fun takeDamage(int: Int) {
        this.currentHealthLevel - sumCurrentDamage
    }
}