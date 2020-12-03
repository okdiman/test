package com.skillbox.skillbox.myapplication

class Recruit(): AbstractWarrior(80, 10, 30, AbstractWeapon.Weapons.createPistol()) {
    override var isKilled: Boolean = false
        get() = TODO("Not yet implemented")
        set(value) {
            if (this.currentHealthLevel < 1) field = true
        }
}