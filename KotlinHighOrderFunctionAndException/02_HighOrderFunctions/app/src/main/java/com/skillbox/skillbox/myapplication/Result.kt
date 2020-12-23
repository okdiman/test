package com.skillbox.skillbox.myapplication

sealed class Result<T, R> {
    data class Success<T, R>(val value: T) {
    }

    data class Error<T, R>(val value: R) {
    }

    fun <T, R> resultReturn(input: Result<out T, R>): Result<out T, R> {
        return input
    }
}