package com.skillbox.skillbox.homework1

fun main (){
    printLeight("dsfsd")
    readLine()?.toIntOrNull()
            ?.let { number ->
                println("вы ввели число $number")
            }
            ?: println("вы ввели не число")
    val string: String = "String"
    val nullString: String? = null
    val leight: Int = string.length
    val nullLeight: Int? = nullString?.length

    nullString?.reversed()
            ?.find { it == '4' }
            ?.isDigit()

    println("String leight is ${nullString?.length?: "incorrect"}")

}

fun printLeight (string: String?){
    string?:return
    print("Длина строки = ${string.length}")
}