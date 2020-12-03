package com.skillbox.skillbox.myapplication

class Battle {
    val team1 = Team()
    val team2 = Team()
    var theBattleIsOver = Boolean

    fun getStateBattle(): StateOfBattle {
        return StateOfBattle.progress
    }

    val deathList = emptyList<Warrior>().toMutableList()

    fun nextItaration() {
        println("Этап битвы начался")
        team1.team.shuffle()
        team2.team.shuffle()
        var n = team1.team.size
        while (n > 0) {
            team1.team.forEach() {
                it.attack(team2.team[n])
                n++
            }
        }
    }
}