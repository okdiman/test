package com.skillbox.skillbox.myapplication


abstract class AbstractWeapon(
    val maxNumberOfBullets: Int,
    val shootingType: FireType
) {
    var listOfAmmo = emptyList<Ammo>().toMutableList()
    var availabilityOfAmmo: Boolean = listOfAmmo.size == 0

    abstract fun createBullet(): Ammo

    val receivingAmmos = mutableListOf<Ammo>()

    fun recharge() {
        println("$this перезарежается")
        var currentNumbersOfAmmo: Int = 0
        if (currentNumbersOfAmmo < maxNumberOfBullets) {
            listOfAmmo.add(createBullet())
            ++currentNumbersOfAmmo
        }
    }

    fun receivingOfAmmo() {
        if (shootingType is FireType.singleShot) {
            receivingAmmos + 1
            listOfAmmo - 1
        } else {
            receivingAmmos + FireType.BurstShooting()
            listOfAmmo - FireType.BurstShooting()
        }
        println("Получены патроны")
    }

    object Weapons {
        fun createPistol(): AbstractWeapon {
            val pistol = object : AbstractWeapon(7, FireType.singleShot) {
                override fun createBullet(): Ammo {
                    return Ammo.PISTOL
                }
            }
            println("Получен пистолет")
            return pistol
        }

        fun createTommyGun(): AbstractWeapon {
            val tommyGun = object : AbstractWeapon(30, FireType.BurstShooting(5)) {
                override fun createBullet(): Ammo {
                    return Ammo.MACHINEGUN
                }
            }
            println("Получен пулемет")
            return tommyGun
        }

        fun createMachineGun(): AbstractWeapon {
            val machineGun = object : AbstractWeapon(40, FireType.BurstShooting(3)) {
                override fun createBullet(): Ammo {
                    return Ammo.MACHINEGUN
                }
            }
            println("Получен автомат")
            return machineGun
        }

        fun createShotGun(): AbstractWeapon {
            val shotGun = object : AbstractWeapon(5, FireType.singleShot) {
                override fun createBullet(): Ammo {
                    return Ammo.SHOTGUN
                }
            }
            println("Получен дробовик")
            return shotGun
        }
    }
}
