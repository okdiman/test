package com.skillbox.skillbox.myapplication


fun main() {
    val queue = Queue <String>()
    queue.enqueue("qwe")
    queue.enqueue("asdfa")
    queue.enqueue("asafd")
    queue.enqueue("asd")
    println(queue.filterNewQueue {it.length > 3})
    val filterQueue = queue.let { Queue<String>::filterNewQueue }
    println(filterQueue)
}
fun <T : Number> genericFunction(list: MutableList<T>) {
    println(list.filter { it is Int }.filter { it.toInt() % 2 == 0 })
    println(list.filter { it is Double })
}

