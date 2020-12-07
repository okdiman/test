package com.skillbox.skillbox.myapplication


abstract class AbstractWeapon(
    val maxNumberOfBullets: Int,
    val shootingType: FireType
) {
    var listOfAmmo = emptyList<Ammo>().toMutableList()
    var availabilityOfAmmo: Boolean = listOfAmmo.size == 0

    abstract fun createBullet(): Ammo

    val receivingAmmos = mutableListOf<Ammo>()

    fun recharge(): MutableList<Ammo> {
        var currentNumbersOfAmmo: Int = 0
        while (currentNumbersOfAmmo < this.maxNumberOfBullets) {
            listOfAmmo.add(createBullet())
            ++currentNumbersOfAmmo
        }
        availabilityOfAmmo = false
        return listOfAmmo
    }

    fun receivingOfAmmo() {
        if (shootingType is FireType.singleShot) {
            receivingAmmos.add(listOfAmmo[0])
            listOfAmmo.removeAt(0)
        } else {
            receivingAmmos.add(listOfAmmo.elementAt(FireType.BurstShooting().sizeOfBurst))
            listOfAmmo.removeAt(FireType.BurstShooting().sizeOfBurst)
        }
        println("Получены патроны из магазина")
    }

    object Weapons {
        fun createPistol(): AbstractWeapon {
            val pistol = object : AbstractWeapon(7, FireType.singleShot) {
                override fun createBullet(): Ammo {
                    return Ammo.PISTOL
                }
            }
            println("Recruit получил пистолет")
            return pistol
        }

        fun createTommyGun(): AbstractWeapon {
            val tommyGun = object : AbstractWeapon(30, FireType.BurstShooting(5)) {
                override fun createBullet(): Ammo {
                    return Ammo.MACHINEGUN
                }
            }
            println("General получил пулемет")
            return tommyGun
        }

        fun createMachineGun(): AbstractWeapon {
            val machineGun = object : AbstractWeapon(40, FireType.BurstShooting(3)) {
                override fun createBullet(): Ammo {
                    return Ammo.MACHINEGUN
                }
            }
            println("Captain получил автомат")
            return machineGun
        }

        fun createShotGun(): AbstractWeapon {
            val shotGun = object : AbstractWeapon(5, FireType.singleShot) {
                override fun createBullet(): Ammo {
                    return Ammo.SHOTGUN
                }
            }
            println("Soldier получил дробовик")
            return shotGun
        }
    }
}
