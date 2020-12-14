package com.skillbox.skillbox.myapplication

sealed class StateOfBattle() {
    object progress : StateOfBattle() {
        fun progress(battle: Battle): Any {
            if (battle.sumHealthTeam1 < 1 && battle.sumHealthTeam2 < 1) {
                return draw.draw()
            }
            if (battle.sumHealthTeam1 < 1) {
                return secondTeamWin.secondTeamWin()
            }
            if (battle.sumHealthTeam2 < 1) {
                return firstTeamWin.firstTeamWin()
            }
            return println("суммарное здоровье первой команды:${battle.sumHealthTeam1}, суммарное здоровье второй команды: ${battle.sumHealthTeam2}")
        }
    }

    object firstTeamWin : StateOfBattle() {
        fun firstTeamWin() {
            return println("Победила команда 1")
        }
    }

    object secondTeamWin : StateOfBattle() {
        fun secondTeamWin() {
            return println("Победила команда 2")
        }
    }

    object draw : StateOfBattle() {
        fun draw() {
            return println("Ничья")
        }
    }
}