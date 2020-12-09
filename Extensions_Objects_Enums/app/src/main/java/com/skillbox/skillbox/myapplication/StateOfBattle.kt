package com.skillbox.skillbox.myapplication

sealed class StateOfBattle() {
    object progress : StateOfBattle() {
        fun progress(): Any {
            if (Battle().sumHealthTeam1 < 1 || Battle().sumHealthTeam2 < 1) {
                println("Ничья")
                draw
                Battle().theBattleIsOver = true
                return firstTeamWin
            }
            if (Battle().sumHealthTeam1 < 1) {
                println("Победила команда 2")
                secondTeamWin
                Battle().theBattleIsOver = true
                return secondTeamWin
            }
            if (Battle().sumHealthTeam2 < 1) {
                println("Победила команда 1")
                firstTeamWin
                Battle().theBattleIsOver = true
                return draw
            }
            return println("суммарное здоровье первой команды:${Battle().sumHealthTeam1}, суммарное здоровье второй команды: ${Battle().sumHealthTeam2}")
        }
    }

    object firstTeamWin : StateOfBattle() {}

    object secondTeamWin : StateOfBattle() {}

    object draw : StateOfBattle() {}
}