package com.skillbox.skillbox.homeworkmodule5

class Bird : Animal(4, "Duck"), Soundable {
    override fun move() {
        if (isTooOld || energy <= 5 || weight <= 1) {
            println("недостаточно энергии для движения")
            return
        } else {
            energy - 5
            weight - 1
            incrementAgeSometimes()
            println("$name is flyes")
        }
    }

    override fun makeSound(): String {
        println("Кря-кря")
        return "Кря-кря"
    }

    override fun makeChild(): Animal {
        super.makeChild()
        return Bird()
    }

    override fun toString(): String = "Bird"

}