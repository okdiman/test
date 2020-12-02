package com.skillbox.skillbox.myapplication


abstract class AbstractWeapon(
    val maxNumberOfBullets: Int,
    val shootingType: FireType
) {
    var listOfAmmo = emptyList<Ammo>().toMutableList()
    var availabilityOfAmmo: Boolean = listOfAmmo.size == 0

    abstract fun createBullet(): Ammo

    fun recharge() {
        listOfAmmo = listOf<Ammo>().toMutableList()
        var currentNumbersOfAmmo: Int = 0
        if (currentNumbersOfAmmo < maxNumberOfBullets) {
            listOfAmmo.add(createBullet())
            ++currentNumbersOfAmmo
        }
    }

    fun receivingOfAmmo() {
        if (shootingType is FireType.singleShot) {
            listOfAmmo - 1
        } else {
            listOfAmmo -
        }
    }

    object Weapons {
        fun createPistol(): AbstractWeapon {
            object : AbstractWeapon(7, FireType.singleShot)
        }

        fun createTommyGun(): AbstractWeapon {
            object : AbstractWeapon(30, FireType.BurstShooting)
        }

        fun createMachineGun(): AbstractWeapon {
            object : AbstractWeapon(40, FireType.BurstShooting)
        }

        fun createShotGun(): AbstractWeapon {
            object : AbstractWeapon(5, FireType.singleShot)
        }
    }
}
