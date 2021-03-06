package com.skillbox.github.data

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface GithubInterface {

    //запрос на получение инофрмации о пользователе
    @GET("user")
    fun searchUsersInformation(
    ): Call<UsersInfo>

    //запрос на получение инофрмации о репозиториях, доступных для пользователя
    @GET("repositories")
    fun searchUsersRepositories(
//        @Header("accept") header: String,
//        @Query("since") query: Int
    ): Call<List<UsersRepository>>

    //запрос на получение инофрмации об отмеченности пользователем выбранного репозиотрия
    @GET("user/starred/{owner}/{repo}")
    fun checkIsStarredOrNot(
        @Path("repo") repo: String,
        @Path("owner") owner: String
    ): Call<String>

    //запрос на проставление отметки пользователем на репозиторий
    @PUT("user/starred/{owner}/{repo}")
    fun addStar(
        @Path("repo") repo: String,
        @Path("owner") owner: String
    ): Call<String>

    //запрос на удаление отметки пользователем на репозиторий
    @DELETE("user/starred/{owner}/{repo}")
    fun delStar(
        @Path("repo") repo: String,
        @Path("owner") owner: String
    ): Call<String>

    //запрос на получение только отмеченных пользователем репозиториев
    @GET("user/starred")
    fun getStarredRepositories(
    ): Call<List<UsersRepository>>
}