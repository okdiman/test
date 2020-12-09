package com.skillbox.skillbox.myapplication

fun main() {
    println("Введите число войнов в каждой команде")
    val battle = Battle()
    var m = 100
    while (m > 0) {
        battle.nextItaration()
        battle.sumHealth1()
        battle.sumHealth2()
        m--
    }
    return
}
