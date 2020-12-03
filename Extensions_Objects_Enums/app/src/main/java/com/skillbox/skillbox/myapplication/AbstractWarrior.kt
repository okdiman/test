package com.skillbox.skillbox.myapplication

import kotlin.random.Random

abstract class AbstractWarrior(
    val maxHealthLevel: Int,
    override val chanceToDodge: Int,
    val hitProbability: Int,
    val weapon: AbstractWeapon
) : Warrior {
    var currentHealthLevel = maxHealthLevel
    var currentDamage: Int = 0

    var hit: Boolean = true

    fun death (){
        if (currentHealthLevel < 1) {
            isKilled = true
        }
    }


    override fun attack(warrior: Warrior) {
        if (weapon.availabilityOfAmmo){
            weapon.recharge()
            return
        } else {
            weapon.receivingOfAmmo()
            weapon.receivingAmmos.forEach(){

            }
        }

    }

    override fun takeDamage(int: Int) {
        this.currentHealthLevel - this.currentDamage
    }
}