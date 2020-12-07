package com.skillbox.skillbox.myapplication

import kotlin.random.Random
import kotlin.random.nextInt


abstract class AbstractWeapon(
    val maxNumberOfBullets: Int,
    val shootingType: FireType
) {
    var listOfAmmo = mutableListOf<Ammo>()
    var availabilityOfAmmo: Boolean = listOfAmmo.size == 0

    abstract fun createBullet(): Ammo

    var receivingAmmos = mutableListOf<Ammo>()

    fun recharge(): MutableList<Ammo> {
        var currentNumbersOfAmmo: Int = 0
        while (currentNumbersOfAmmo < this.maxNumberOfBullets) {
            listOfAmmo.add(createBullet())
            ++currentNumbersOfAmmo
        }
        availabilityOfAmmo = false
        println("заряжено ${listOfAmmo.size} патронов")
        return listOfAmmo
    }

    fun receivingOfAmmo() {
        receivingAmmos = mutableListOf<Ammo>()
        if (shootingType is FireType.singleShot) {
            receivingAmmos.add(listOfAmmo[0])
            listOfAmmo.removeAt(0)
            println("осталось ${listOfAmmo.size} ")
        }
        if (shootingType is FireType.BurstShooting) {
            receivingAmmos = listOfAmmo.take(Random.nextInt(3..5)).toMutableList()
            listOfAmmo 
            println("осталось ${listOfAmmo.size} патронов")
        }
        println("Получены патроны из магазина в количестве ${receivingAmmos.size}")
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
            val tommyGun = object : AbstractWeapon(40, FireType.BurstShooting(5)) {
                override fun createBullet(): Ammo {
                    return Ammo.MACHINEGUN
                }
            }
            println("General получил пулемет")
            return tommyGun
        }

        fun createMachineGun(): AbstractWeapon {
            val machineGun = object : AbstractWeapon(30, FireType.BurstShooting(3)) {
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
