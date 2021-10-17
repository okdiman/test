package com.skillbox.skillbox.testonlineshop.domain.daggermodules

import com.skillbox.skillbox.testonlineshop.data.network.ApiKeyInterceptor
import com.skillbox.skillbox.testonlineshop.data.network.MyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesClient(): OkHttpClient {
        return OkHttpClient.Builder()
//        добавляем наш интерцептор для добавления ключа
            .addInterceptor(ApiKeyInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        //    создаем объект ретрофита
        return Retrofit.Builder()
            .baseUrl("https://db2021ecom-edca.restdb.io/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun providesApi(retrofit: Retrofit): MyApi {
        //    связываем наш Api интерфейс и ретрофит
        return retrofit.create()
    }
}