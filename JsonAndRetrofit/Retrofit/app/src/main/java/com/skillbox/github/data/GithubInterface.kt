package com.skillbox.github.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GithubInterface {
    @GET("user")
    fun searchUsersInformation(): Call<String>

    @GET("repositories")
    fun searchUsersRepositories(
//        @Header("accept") header: String,
//        @Query("since") query: Int
    ): Call<ServerItemsWrapper<UsersRepository>>
}