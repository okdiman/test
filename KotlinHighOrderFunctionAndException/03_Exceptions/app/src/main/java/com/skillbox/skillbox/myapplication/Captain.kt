package com.skillbox.skillbox.myapplication

class Captain() : AbstractWarrior(100, 20, 70, AbstractWeapon.Weapons.createMachineGun()) {
    override var isKilled: Boolean = false
        get(){
            return field
        }
        set(value) {
            if (this.currentHealthLevel < 1) {
                field = true
            }
        }

    override fun toString(): String {
        return "Captain"
    }
}