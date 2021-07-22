package com.skillbox.github.data

import retrofit2.http.GET

interface GithubInterface {
    @GET("user")
    fun searchUsersInformation(){

    }
}