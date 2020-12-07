package com.skillbox.skillbox.myapplication

import kotlin.random.Random

abstract class AbstractWarrior(
    val maxHealthLevel: Int,
    override val chanceToDodge: Int,
    val hitProbability: Int,
    val weapon: AbstractWeapon
) : Warrior {

    var currentHealthLevel = maxHealthLevel

    var sumCurrentDamage: Int = 0

    var hit: Boolean = true

    override fun attack(warrior: Warrior): Any {
        if (weapon.availabilityOfAmmo) {
            println("$this перезарежается")
            return weapon.recharge()
        } else {
            println("$this прицелился в $warrior")
            weapon.receivingOfAmmo()
            weapon.receivingAmmos.forEach() {
                hit = when {
                    Random.nextInt(100 + 1) < this.hitProbability && Random.nextInt(100 + 1) > warrior.chanceToDodge -> true
                    else -> false
                }
                if (hit) {
                    val l = sumCurrentDamage + it.currentDamage()
                    sumCurrentDamage = l
                    println("$this наносит $warrior $sumCurrentDamage урона")
                    warrior.takeDamage(sumCurrentDamage)
                } else {
                    println("$this промахнулся по $warrior")
                    it.currentDamage()
                    sumCurrentDamage = 0
                }
            }
            return sumCurrentDamage
        }
    }

    override fun takeDamage(int: Int): Int {
        this.currentHealthLevel -= sumCurrentDamage
        if (this.currentHealthLevel < 1) {
            println("$this убит")
            this.isKilled = true
            Team().deathList.add(this)
        }
        return this.currentHealthLevel
    }
}