package com.skillbox.skillbox.myapplication

class Recruit(): AbstractWarrior(80, 10, 50, AbstractWeapon.Weapons.createPistol()) {
    override var isKilled: Boolean = false
        get() = TODO("Not yet implemented")
        set(value) {
            if (this.currentHealthLevel < 1) field = true
        }

    override fun toString(): String {
        return "Recruit"
    }
}