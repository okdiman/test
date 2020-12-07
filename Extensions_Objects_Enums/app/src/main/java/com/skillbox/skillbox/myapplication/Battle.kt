package com.skillbox.skillbox.myapplication

class Battle {
    val team1 = Team().team
    val team2 = Team().team
    var theBattleIsOver: Boolean = false

    fun getStateBattle(): Any {
        return StateOfBattle.progress.progress()
    }

    val deathList = emptyList<Warrior>().toMutableList()

    fun nextItaration() {
        println("Этап битвы начался")
        team1.shuffle()
        team2.shuffle()
        var n = team2.size
        while (n > 0) {
            team1.forEach() {
                val attack = it.attack(team2[n - 1])
                n--
                if (attack is Int) {
                    if (!it.weapon.availabilityOfAmmo) {
                        team2[n].takeDamage(attack)
                    }
                    println("уровень здоровья противника после атаки ${team2[n].currentHealthLevel}")
                }
            }
        }
        n = team1.size
        while (n > 0) {
            team2.forEach() {
                val attack = it.attack(team1[n - 1])
                n--
                if (attack is Int) {
                    if (!it.weapon.availabilityOfAmmo) {
                        team1[n].takeDamage(attack)
                    }
                    println("уровень здоровья противника после атаки ${team1[n].currentHealthLevel}")
                }
            }
        }
        team1.removeAll(deathList)
        team2.removeAll(deathList)
        return
    }

    fun sumHealth1(): Int {
        var sumHealth: Int = 0
        team1.forEach() {
            sumHealth += it.currentHealthLevel
        }
        println("общее здоровье команды 1: $sumHealth")
        return sumHealth
    }

    fun sumHealth2(): Int {
        var sumHealth: Int = 0
        team2.forEach() {
            sumHealth += it.currentHealthLevel
        }
        println("общее здоровье команды 2: $sumHealth")
        return sumHealth
    }
}