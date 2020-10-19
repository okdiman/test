package com.skillbox.skillbox.homework

fun main() {

    val firstname = "Dmitrii"
    val lastName = "Okunev"
    var height = 178
    val weight = 78f
    var isChild: Boolean = height <= 150 || weight <= 40
    var info = "I am $firstname $lastName, my height is $height and weight is $weight and ${if (isChild) "i am a child" else "i am not a child"}"
    println(info)
    height = 135
    isChild = height <= 150 || weight <= 40
    info = "I am $firstname $lastName, my height is $height and weight is $weight and ${if (isChild) "i am a child" else "i am not a child"}"
    println(info)
}