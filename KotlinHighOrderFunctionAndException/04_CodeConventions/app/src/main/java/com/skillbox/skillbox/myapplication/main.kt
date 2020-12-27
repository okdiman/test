package com.skillbox.skillbox.myapplication

fun main() {
    println("Введите число войнов в каждой команде")
    val battle = Battle()
    while (!battle.theBattleIsOver) {
        battle.nextItaration()
        battle.sumHealth1()
        battle.sumHealth2()
        battle.getStateBattle(battle)
    }
}
