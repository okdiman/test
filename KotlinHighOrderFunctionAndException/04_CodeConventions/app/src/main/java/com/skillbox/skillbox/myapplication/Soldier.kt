package com.skillbox.skillbox.myapplication

class Soldier() : AbstractWarrior(100, 15, 60, AbstractWeapon.Weapons.createShotGun()) {
    override var isKilled: Boolean = false
        get() {
            return field
        }
        set(value) {
            if (this.currentHealthLevel < 1) {
                field = true
            }
        }

    override fun toString(): String {
        return "Soldier"
    }
}
