package com.skillbox.skillbox.lists1

sealed class Person {
    data class User(
        val name: String,
        val avatarLink: Int,
        val age: Int
    ) : Person()

    data class Developer(
        val name: String,
        val avatarLink: Int,
        val age: Int,
        val programmingLanguage: String
    ) : Person()
}