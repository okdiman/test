package com.skillbox.skillbox.homeworkmodule5

class Fish : Animal(maxAge = 5, name = "shark") {
    override fun move() {
        super.move()
        println("swims")
    }

    override fun makeChild(): Animal {
        super.makeChild()
        return Fish()
    }

    override fun toString(): String = "Fish"

    override fun makeSound() = ""

}