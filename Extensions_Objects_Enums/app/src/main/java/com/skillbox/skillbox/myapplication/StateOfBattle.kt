package com.skillbox.skillbox.myapplication

sealed class StateOfBattle() {
    object progress : StateOfBattle() {
        fun progress() {
            println("суммарное здоровье первой команды:${Battle().sumHealth1()}, суммарное здоровье второй команды: ${Battle().sumHealth2()}")
            if (Battle().sumHealth1() < 1 && Battle().sumHealth2() < 1) {
                println("Ничья")
                draw
                Battle().theBattleIsOver = true
            }
            if (Battle().sumHealth1() < 1) {
                println("Победила команда 2")
                secondTeamWin
                Battle().theBattleIsOver = true
            }
            if (Battle().sumHealth2() < 1) {
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