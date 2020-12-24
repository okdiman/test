package com.skillbox.skillbox.myapplication

interface Warrior {
    var isKilled: Boolean
    val chanceToDodge: Int

    fun attack(warrior: Warrior) : Any
    fun takeDamage(int: Int): Int
}