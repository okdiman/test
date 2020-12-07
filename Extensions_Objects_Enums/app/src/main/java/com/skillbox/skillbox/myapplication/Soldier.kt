package com.skillbox.skillbox.myapplication

class Soldier(): AbstractWarrior(120, 15, 60, AbstractWeapon.Weapons.createShotGun()) {
    override var isKilled: Boolean = false
        get() = TODO("Not yet implemented")
        set(value) {
            if (this.currentHealthLevel < 1) field = true
        }

    override fun toString(): String {
        return "Soldier"
    }
}