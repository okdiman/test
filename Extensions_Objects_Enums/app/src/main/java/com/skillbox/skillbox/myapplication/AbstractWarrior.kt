package com.skillbox.skillbox.myapplication

abstract class AbstractWarrior(
    val maxHealthLevel: Int,
    override val chanceToDodge: Int,
    val hitProbability: Int,
    val weapon: AbstractWeapon
) : Warrior {
    var currentHealthLevel = maxHealthLevel

    override fun attack(warrior: Warrior) {

    }

    override fun takeDamage(int: Int) {
        
    }
}