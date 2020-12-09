package com.skillbox.skillbox.myapplication

class Battle {
    val team1 = Team().team
    val team2 = Team().team
    var theBattleIsOver: Boolean = false

    fun getStateBattle(): Any {
        return StateOfBattle.progress.progress()
    }


    fun nextItaration() {

        println("Этап битвы начался")
        team1.shuffle()
        team2.shuffle()
        var attackedWarrior = team2.size
        while (attackedWarrior > 0) {
            team1.forEach() {
                val attack = it.attack(team2[attackedWarrior - 1])
                println("уровень здоровья противника после атаки ${team2[attackedWarrior - 1].currentHealthLevel}")
                if (team2[attackedWarrior - 1].isKilled){
                    team2.remove(team2[attackedWarrior - 1])
                }
                attackedWarrior--
                if (team2.size == 0){
                    theBattleIsOver = true
                    println("все воины противника убиты, битва окончена")
                    return
                }
            }
        }
        attackedWarrior = team1.size
        while (attackedWarrior > 0) {
            team2.forEach() {
                val attack = it.attack(team1[attackedWarrior - 1])
                println("уровень здоровья противника после атаки ${team1[attackedWarrior - 1].currentHealthLevel}")
                if (team1[attackedWarrior - 1].isKilled){
                    team1.remove(team1[attackedWarrior - 1])
                }
                attackedWarrior--
                if (team1.size == 0){
                    theBattleIsOver = true
                    println("все воины противника убиты, битва окончена")
                    return
                }
            }
        }
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