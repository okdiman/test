package com.skillbox.skillbox.myapplication

import kotlin.random.Random
import kotlin.random.nextInt

sealed class FireType() {
    object singleShot : FireType()

    data class BurstShooting(
        val sizeOfBurst: Int = Random.nextInt(3..5)
    ) : FireType()
}
