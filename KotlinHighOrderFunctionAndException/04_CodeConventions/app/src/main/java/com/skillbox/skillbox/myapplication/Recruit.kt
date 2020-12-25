package com.skillbox.skillbox.myapplication

class Recruit() : AbstractWarrior(100, 10, 50, AbstractWeapon.Weapons.createPistol()) {
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
        return "Recruit"
    }
}
