package com.skillbox.skillbox.homeworkmodule5

class Fish : Animal (maxAge = 5, name = "shark") {
    override fun move() {
        super.move()
        println("swims")
    }

    override fun makeChild(): Animal {
        super.makeChild()
        println ("make Child $this, his name is ${this.name}, his weight is ${this.weight}, and this energy is ${this.energy}")
        return Fish()
    }

    override fun toString(): String = "Fish"

    override fun makeSound() = ""

}