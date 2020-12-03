package com.skillbox.skillbox.myapplication

class General(): AbstractWarrior(200,40,80, AbstractWeapon.Weapons.createTommyGun()) {
   override var isKilled: Boolean = false
      get() = TODO("Not yet implemented")
      set(value) {
         if (this.currentHealthLevel < 1) field = true
      }
   }