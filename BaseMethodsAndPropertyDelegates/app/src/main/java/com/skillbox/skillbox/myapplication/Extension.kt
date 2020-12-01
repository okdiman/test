package com.skillbox.skillbox.myapplication

fun main() {
    1.toBoolean()
    1.isPositive

    val nullable: Int? = null
    nullable?.orDefault(0)
}

fun Int.toBoolean(): Boolean {
    return this != 0
}

val Int.isPositive: Boolean
    get() = this > 0

fun Int.orDefault (defaultValue: Int): Int{
    return this ?: defaultValue
}