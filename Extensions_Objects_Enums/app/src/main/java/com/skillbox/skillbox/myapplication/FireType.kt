package com.skillbox.skillbox.myapplication

sealed class FireType() {
    object singleShot : FireType()

    data class BurstShooting(
        val sizeOfBurst: Int
    ) : FireType()


}