package com.skillbox.skillbox.myapplication

sealed class StateOfBattle () {
    object progress: StateOfBattle(){
        val progress = Team
    }
    object firstTeamWin: StateOfBattle(){

    }
    object secondTeamWin: StateOfBattle(){

    }
    object draw: StateOfBattle(){

    }
}