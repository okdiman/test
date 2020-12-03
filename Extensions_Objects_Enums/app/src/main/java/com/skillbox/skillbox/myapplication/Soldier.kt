package com.skillbox.skillbox.myapplication

class Soldier(): AbstractWarrior(120, 20, 50, AbstractWeapon.Weapons.createShotGun()) {
    override var isKilled: Boolean = false
        get() = TODO("Not yet implemented")
        set(value) {
            if (this.currentHealthLevel < 1) field = true
        }
}