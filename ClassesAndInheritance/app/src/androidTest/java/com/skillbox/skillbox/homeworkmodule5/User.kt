package com.skillbox.skillbox.homeworkmodule5

data class User(
        val name: String,
        val lastName: String,
        val age: Int = 0
) {
    var innerState: String = ""
    fun getFullnameLenght () = "$name$lastName".length
}