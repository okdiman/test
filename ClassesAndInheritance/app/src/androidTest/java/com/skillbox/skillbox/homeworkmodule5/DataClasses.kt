package com.skillbox.skillbox.homeworkmodule5

import com.skillbox.skillbox.homework3.Car

fun main() {
    val user = User("Dima", "Okunev")
    val user2 = User("Ksusha", "Okuneva")

    println(user == user2)

    val user3 = user.copy("Roma")

    val (name, lastname) = User("Alex", "Buttner")
    println("his name is $name, lastname is $lastname")

    val users = listOf(
        user,
        user2,
        user3
    )
    users.forEach { (name, lastname) -> println("$name $lastname") }
    val (wheelCount, doorCount) = Car(4, 2, 2000, 1800, 1600)

    val map = mapOf(
        1 to "1",
        2 to "2"
    )
    for ((key, value) in map) {
        key
        value
    }
}
