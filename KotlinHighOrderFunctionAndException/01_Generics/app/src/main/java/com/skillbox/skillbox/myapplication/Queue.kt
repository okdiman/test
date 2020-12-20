package com.skillbox.skillbox.myapplication

class Queue<T>(item: T) {
    private val queueList = mutableListOf<T>()
    fun enqueue(item: T) {
        queueList.add(item)
    }

    fun dequeue(): T? {
        return if (queueList.isEmpty()) null
        else queueList[0]
    }
}