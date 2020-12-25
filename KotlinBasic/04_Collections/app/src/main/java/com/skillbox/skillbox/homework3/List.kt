package com.skillbox.skillbox.homework3

fun main() {
    val mutableList = mutableListOf("1", "2", "3")
    println(mutableList)

    mutableList[2]

    mutableList.forEach() { print(it) }

    val emptyList = emptyList<String>()

    mutableList [0] = ("1.2")
    println(mutableList)

    mutableList.removeAt(1)
    mutableList.add("4")
    mutableList.add(1, "new 1")
    println(mutableList)

    mutableList.addAll(listOf("10", "20"))
    println(mutableList)
}
