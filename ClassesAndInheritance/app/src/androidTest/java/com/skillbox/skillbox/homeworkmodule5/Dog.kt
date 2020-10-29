package com.skillbox.skillbox.homeworkmodule5

class Dog : Animal(6, "sheep-dog"), Soundable {
    override fun move() {
        super.move()
        println("runs")
    }

    override fun makeSound() = "Гав-гав"


    override fun makeChild(): Animal {
        super.makeChild()
        println ("make Child $this, his name is ${this.name}, his weight is ${this.weight}, and this energy is ${this.energy}")
        return Dog()
    }
    override fun toString(): String = "Dog"
}