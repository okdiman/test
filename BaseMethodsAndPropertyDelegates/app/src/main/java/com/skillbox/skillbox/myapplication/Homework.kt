package com.skillbox.skillbox.myapplication

fun main() {
    val person1 = Person(178, 78, "Dima")
    val person2 = Person(178, 78, "Dima")

    val set = hashSetOf(
        person1,
        person2
    )

    println(set.size)

    val person3 = Person(158, 40, "Ksusha")

    set.add(person3)

    set.forEach() {
        println(it.toString())
    }

    person1.buyPet()
    person1.buyPet()
    person2.buyPet()
    person2.buyPet()
    person2.buyPet()
    person3.buyPet()
    person3.buyPet()
    person3.buyPet()
    person3.buyPet()

    set.forEach() {
        println("${it.name}, ${it.pets}")
    }

}