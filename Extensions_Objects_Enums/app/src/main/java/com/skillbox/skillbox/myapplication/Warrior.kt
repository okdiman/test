package com.skillbox.skillbox.myapplication

interface Warrior {
    var isKilled: Boolean
    val chanceToDodge: Int

    fun attack(warrior: Warrior)
    fun takeDamage(int: Int)
}