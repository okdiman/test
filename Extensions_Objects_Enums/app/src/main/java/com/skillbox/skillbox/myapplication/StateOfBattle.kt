package com.skillbox.skillbox.myapplication

sealed class StateOfBattle (team: Team) {
    object progress: StateOfBattle(Team()){

    }
    object firstTeamWin: StateOfBattle(Team()){

    }
    object secondTeamWin: StateOfBattle(Team()){

    }
    object draw: StateOfBattle(Team()){

    }
}