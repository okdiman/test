package com.skillbox.skillbox.homeworkmodule5

class Dog : Animal(6, "sheep-dog"), Soundable {
    override fun move() {
        if (isTooOld || energy <= 5 || weight <= 1) {
            println("недостаточно энергии для движения")
            return
        } else {
            energy - 5
            weight - 1
            incrementAgeSometimes()
            println("$name is runs")
        }
    }

    override fun makeSound(): String {
        println("гав-гав")
        return "Гав-гав"
    }

    override fun makeChild(): Animal {
        super.makeChild()
        return Dog()
    }

    override fun toString(): String = "Dog"
}
