package com.skillbox.skillbox.myapplication

class Battle {
    val team1 = Team().team
    val team2 = Team().team
    var theBattleIsOver: Boolean = false

    fun getStateBattle(): Any {
        return StateOfBattle.progress.progress()
    }

    var sumHealthTeam1: Int = 0

    var sumHealthTeam2: Int = 0


    fun nextItaration() {
        if (this.theBattleIsOver) {
            this.getStateBattle()
        }
        println("Этап битвы начался")
        team1.shuffle()
        team2.shuffle()
        var attackedWarrior = team2.size - 1
        team1.forEach() {
            val attack = it.attack(team2[attackedWarrior])
            println("уровень здоровья противника после атаки ${team2[attackedWarrior].currentHealthLevel}")
            if (team2[attackedWarrior].isKilled) {
                team2.remove(team2[attackedWarrior])
            }
            if (attackedWarrior > 0) attackedWarrior--
            if (team2.size == 0) {
                theBattleIsOver = true
                println("все воины противника убиты, битва окончена, победила команда 1")
                return
            }
        }
        attackedWarrior = team1.size - 1
        team2.forEach() {
            val attack = it.attack(team1[attackedWarrior])
            println("уровень здоровья противника после атаки ${team1[attackedWarrior].currentHealthLevel}")
            if (team1[attackedWarrior].isKilled) {
                team1.remove(team1[attackedWarrior])
            }
            if (attackedWarrior > 0) attackedWarrior--
            if (team1.size == 0) {
                theBattleIsOver = true
                println("все воины противника убиты, битва окончена, победила команда 2")
                return
            }
        }
        return
    }

    fun sumHealth1(): Int {
        sumHealthTeam1 = 0
        team1.forEach() {
            sumHealthTeam1 += it.currentHealthLevel
        }
        println("общее здоровье команды 1: $sumHealthTeam1")
        return sumHealthTeam1
    }

    fun sumHealth2(): Int {
        sumHealthTeam2 = 0
        team2.forEach() {
            sumHealthTeam2 += it.currentHealthLevel
        }
        println("общее здоровье команды 2: $sumHealthTeam2")
        return sumHealthTeam2
    }
}