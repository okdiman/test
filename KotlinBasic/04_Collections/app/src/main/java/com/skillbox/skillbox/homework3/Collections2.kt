package com.skillbox.skillbox.homework3

fun main (){
    val array = arrayOf("Димитрио", "Ксюшка", "Роман")

    for (user in array){
        println(user)
    }
    array.forEach { println(it) }
    array.forEachIndexed { index, user -> println("index = $index, element - $user")  }
    val firstElement = array [0]
    val lastElement = array [array.lastIndex]
    array [2] = "Romeo"
    array.forEach { println(it) }

    val largeArray = Array(100){index -> "User #$index"}
    largeArray.forEach { println(it) }
}