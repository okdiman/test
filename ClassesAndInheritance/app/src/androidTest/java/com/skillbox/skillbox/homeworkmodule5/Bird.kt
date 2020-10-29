package com.skillbox.skillbox.homeworkmodule5

class Bird : Animal(4, "Duck"), Soundable {
    override fun move() {
        super.move()
        println("flyes")
    }

    override fun makeSound() = "Кря-Кря"

    override fun makeChild(): Animal {
        super.makeChild()
        return Bird()
    }

    override fun toString(): String = "Bird"

}