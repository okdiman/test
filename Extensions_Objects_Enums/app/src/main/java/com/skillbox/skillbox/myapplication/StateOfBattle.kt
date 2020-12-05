package com.skillbox.skillbox.myapplication

sealed class StateOfBattle() {
    object progress : StateOfBattle() {
        fun progress() {
            println("суммарное здоровье первой команды:${Battle().team1.sumHealth()}, суммарное здоровье второй команды: ${Battle().team2.sumHealth()}")
            if (Battle().team1.sumHealth() < 1 && Battle().team2.sumHealth() < 1) {
                println("Ничья")
                draw
                Battle().theBattleIsOver = true
            }
            if (Battle().team1.sumHealth() < 1) {
                println("Победила команда 2")
                secondTeamWin
                Battle().theBattleIsOver = true
            }
            if (Battle().team2.sumHealth() < 1) {
                println("Победила команда 1")
                firstTeamWin
                Battle().theBattleIsOver = true
            }
        }
    }

    object firstTeamWin : StateOfBattle() {}

    object secondTeamWin : StateOfBattle() {}

    object draw : StateOfBattle() {}
}