package com.skillbox.skillbox.homeworkmodule5

import kotlin.random.Random

fun main() {

    val zoo = Zoo()

    zoo.listOfAnimals.add(Dog())
    zoo.listOfAnimals.add(Dog())
    zoo.listOfAnimals.add(Bird())
    zoo.listOfAnimals.add(Bird())
    zoo.listOfAnimals.add(Bird())
    zoo.listOfAnimals.add(Bird())
    zoo.listOfAnimals.add(Bird())
    zoo.listOfAnimals.add(Fish())
    zoo.listOfAnimals.add(Fish())
    zoo.listOfAnimals.add(Fish())


    fun life(zoo: Zoo): Any {
        println("Введите желаемое число итераций:")
        val n = readLine()?.toIntOrNull() ?: return "вы ввели не число"
        var m = n
        val newBornList = emptyList<Animal>().toMutableList()
        val deathList = emptyList<Animal>().toMutableList()
        while (m > 0) {
            if (zoo.listOfAnimals.size > 0) {
                zoo.listOfAnimals.forEach() {
                    if (!it.isTooOld) {
                        val randomNumber = Random.nextInt(5)
                        when (randomNumber) {
                            0 -> it.eat()
                            1 -> it.move()
                            2 -> it.sleep()
                            3 -> {
                                it.makeChild()
                                newBornList.add(it)
                            }
                            4 -> it.makeSound()
                        }
                    } else {
                        println("$it died")
                        deathList.add(it)
                    }
                }
                zoo.listOfAnimals.removeAll(deathList)
                zoo.listOfAnimals.addAll(newBornList)
                m--
                println("В зоопарке осталось ${zoo.listOfAnimals.size} животных")
            } else {
                return println("В зоопарке не осталось животных")
            }
        }
        return "Итерация выполнена в количестве $n раз"
    }
    print(life(zoo))
}