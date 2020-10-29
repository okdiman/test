package com.skillbox.skillbox.homeworkmodule5

class Bird: Animal (4, "Duck"), Soundable{
    override fun move() {
        super.move()
        println("flyes")
    }

    override fun makeSound() = "Кря-Кря"

    override fun makeChild(): Animal {
        super.makeChild()
        println ("make Child $this, his name is ${this.name}, his weight is ${this.weight}, and this energy is ${this.energy}")
        return Bird()
    }
    override fun toString(): String = "Bird"

}