package com.skillbox.skillbox.myapplication

class Captain() : AbstractWarrior(150,30, 60, AbstractWeapon.Weapons.createMachineGun()) {
    override var isKilled: Boolean
        get() = TODO("Not yet implemented")
        set(value) {
            if (this.currentHealthLevel < 1){
                isKilled = true
            }
        }
}