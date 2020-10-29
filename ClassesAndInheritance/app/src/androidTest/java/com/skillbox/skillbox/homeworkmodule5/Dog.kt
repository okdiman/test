package com.skillbox.skillbox.homeworkmodule5

class Dog : Animal(6, "sheep-dog"), Soundable {
    override fun move() {
        super.move()
        println("runs")
    }

    override fun makeSound() = "Гав-гав"


    override fun makeChild(): Animal {
        super.makeChild()
        return Dog()
    }

    override fun toString(): String = "Dog"
}