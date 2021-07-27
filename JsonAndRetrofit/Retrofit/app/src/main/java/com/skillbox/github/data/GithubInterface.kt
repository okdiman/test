package com.skillbox.github.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubInterface {
    @GET("user")
    fun searchUsersInformation(
    ): Call<UsersInfo>

    @GET("repositories")
    fun searchUsersRepositories(
//        @Header("accept") header: String,
//        @Query("since") query: Int
    ): Call<List<UsersRepository>>

    @GET("user/starred/{owner}/{repo}")
    fun checkIsStarredOrNot(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<String>
}