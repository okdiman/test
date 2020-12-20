package com.skillbox.skillbox.myapplication


fun main() {
}
fun <T : Number> genericFunction(list: MutableList<T>) {
    println(list.filter { it is Int }.filter { it.toInt() % 2 == 0 })
    println(list.filter { it is Double })
}

