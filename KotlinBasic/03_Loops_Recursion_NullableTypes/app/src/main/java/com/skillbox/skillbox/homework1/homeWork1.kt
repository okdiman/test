package com.skillbox.skillbox.homework1

fun main(){
    println("Введите число")
    val n = readLine()?.toIntOrNull()?: return

    println("Вы ввели число: $n")

    println("Сумма, полученная с помощью While: ${calculateSumByWhile(n)}")

    println(continueFun(n))

    println(doWhile1(n))

    println(calculationForSum(n))

}
fun calculateSumByWhile (n:Int): Int {
    var sum = 0
    var currentNumber = 0
    while (currentNumber<=n){
        sum += currentNumber
        currentNumber ++
    }
    return sum
}
fun continueFun (n:Int) {
    var currentNumber = 0
    while (currentNumber<=n){
        val numberBefore: Int = currentNumber
        currentNumber++
        if (numberBefore % 2 == 1) continue
        println(numberBefore)
    }
}
fun doWhile1 (n: Int): Int {
    var sum = 0
    var currentNumber = 0
    do {
        sum += currentNumber
        currentNumber ++
            }while (currentNumber<=n)
    return sum
}

fun calculationForSum (n: Int): Int {
    var sum = 0
    val range = 0..n
    for (currentNumber in range) {
        sum +=currentNumber
    }
    return sum
}