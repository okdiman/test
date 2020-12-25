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
//        sumCurrentDamage = 0
//        if (weapon.availabilityOfAmmo) {
//            println("$this перезарежается")
//            return weapon.recharge()
//        } else {
        sumCurrentDamage = 0
        println("$this прицелился в $warrior")
        try {
            weapon.receivingOfAmmo()
        } catch (t: NoAmmoException) {
            println("no ammo exception, need to recharge weapon")
            return weapon.recharge()
        }
        var unShotAmmos = weapon.receivingAmmos.size
        weapon.receivingAmmos.forEach() {
            if (warrior.isKilled) {
                println("$warrior мертв, нет смысла стрелять по нему снова")
                println("патроны в количестве $unShotAmmos штук возвращены в магазин")
                var returnedAmmos = unShotAmmos
                while (returnedAmmos > 0) {
                    weapon.listOfAmmo.add(weapon.createBullet())
                    --returnedAmmos
                }
                return println("в магазине ${weapon.listOfAmmo.size} патронов")
            }
            hit = when {
                Random.nextInt(100 + 1) < this.hitProbability && Random.nextInt(100 + 1) > warrior.chanceToDodge -> true
                else -> false
            }
            if (hit) {
                val damagePerOneShot = it.currentDamage()
                sumCurrentDamage += damagePerOneShot
                println("$this наносит $warrior $damagePerOneShot урона за выстрел")
                warrior.takeDamage(damagePerOneShot)
            } else {
                println("$this промахнулся по $warrior")
            }
            --unShotAmmos
        }
        println("всего $this нанес $sumCurrentDamage урона по $warrior")
        return sumCurrentDamage
    }
//    }

    override fun takeDamage(int: Int): Int {
        this.currentHealthLevel -= int
        println("нанесено $int урона")
        if (this.currentHealthLevel < 1) {
            println("$this убит")
            this.currentHealthLevel = 0
            this.isKilled = true
        }
        return this.currentHealthLevel
    }
}
