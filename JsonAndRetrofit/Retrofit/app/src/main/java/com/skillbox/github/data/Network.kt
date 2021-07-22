package com.skillbox.github.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Network {
    lateinit var accessToken: String

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(CustomInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()

    val githubApi: GithubInterface
        get() = retrofit.create()
}