package com.skillbox.skillbox.homeworkmodule5

class Fish : Animal(maxAge = 5, name = "shark"), Soundable {


    override fun move() {
        if (isTooOld || energy <= 5 || weight <= 1) {
            println("недостаточно энергии для движения")
            return
        } else {
            energy - 5
            weight - 1
            incrementAgeSometimes()
            println("$name is swims")
        }
    }

    override fun makeChild(): Animal {
        super.makeChild()
        return Fish()
    }

    override fun toString(): String = "Fish"

}