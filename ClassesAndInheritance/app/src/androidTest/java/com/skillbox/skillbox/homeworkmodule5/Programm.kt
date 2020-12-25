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
    zoo.listOfAnimals.add(Animal(7, "raccoon"))
    zoo.listOfAnimals.add(Animal(7, "raccoon"))

    fun life(zoo: Zoo): Any {
        println("Введите желаемое число итераций:")
        val n = readLine()?.toIntOrNull() ?: return "вы ввели не число"
        if (n > 0) {
            var currentNumber = n
            var newBornList = emptyList<Animal>().toMutableList()
            var deathList = emptyList<Animal>().toMutableList()
            while (currentNumber > 0) {
                if (zoo.listOfAnimals.size > 0) {
                    zoo.listOfAnimals.forEach() {
                        if (!it.isTooOld) {
                            if (it is Fish) {
                                val randomNumber = Random.nextInt(4)
                                when (randomNumber) {
                                    0 -> it.eat()
                                    1 -> it.move()
                                    2 -> it.sleep()
                                    3 -> {
                                        val child = it.makeChild()
                                        newBornList.add(child)
                                        println("make Child $child, his name is ${child.name}, his weight is ${child.weight}, and this energy is ${child.energy}")
                                    }
                                }
                            } else {
                                val randomNumber = Random.nextInt(5)
                                when (randomNumber) {
                                    0 -> it.eat()
                                    1 -> it.move()
                                    2 -> it.sleep()
                                    3 -> {
                                        val child = it.makeChild()
                                        newBornList.add(child)
                                        println("make Child $child, his name is ${child.name}, his weight is ${child.weight}, and this energy is ${child.energy}")
                                    }
                                    4 -> it.makeSound()
                                }
                            }
                        } else {
                            println("$it died")
                            deathList.add(it)
                        }
                    }
                    zoo.listOfAnimals.removeAll(deathList)
                    deathList = emptyList<Animal>().toMutableList()
                    zoo.listOfAnimals.addAll(newBornList)
                    newBornList = emptyList<Animal>().toMutableList()
                    currentNumber--
                    println("Количество оставшихся животных: ${zoo.listOfAnimals.size}")
                } else {
                    return println("В зоопарке не осталось животных")
                }
            }
        } else {
            println("Введите корректное число")
        }
        return "Итерация выполнена в количестве $n раз"
    }
    print(life(zoo))
}
