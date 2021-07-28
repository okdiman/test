package com.skillbox.github.data

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
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
        @Path("repo") repo: String,
        @Path("owner") owner: String
    ): Call<String>

    @PUT("user/starred/{owner}/{repo}")
    fun addStar(
        @Path("repo") repo: String,
        @Path("owner") owner: String
    ): Call<String>

    @DELETE("user/starred/{owner}/{repo}")
    fun delStar(
        @Path("repo") repo: String,
        @Path("owner") owner: String
    ): Call<String>

    @GET("user/starred")
    fun getStarredRepositories(
    ): Call<List<UsersRepository>>
}