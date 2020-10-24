package com.skillbox.skillbox.homework3

fun main (){
    val cats = listOf("Мурзик", "Пушистик", "Элвис")
    println(cats.getOrElse(4){"incorrect"})
    val cat = cats.getOrNull (4) ?: "incorrect"
    println(cat)
}