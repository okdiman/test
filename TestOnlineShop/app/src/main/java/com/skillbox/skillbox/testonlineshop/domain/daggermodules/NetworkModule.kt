package com.skillbox.skillbox.testonlineshop.domain.daggermodules

import android.util.Log
import com.skillbox.skillbox.testonlineshop.data.network.MyApi
import com.skillbox.skillbox.testonlineshop.data.network.apiKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesApiKeyInterceptor(): Interceptor {
        return Interceptor { chain -> //получаем оригинальный запрос
            val originalRequest = chain.request()
            //добавляем header нашего ключа
            val modifiedRequest = originalRequest.newBuilder()
                .addHeader("x-apikey", apiKey)
                .build()
            Log.i("token", apiKey)
            //передаем дальше модифицированный запрос
            chain.proceed(modifiedRequest)
        }
    }

    @Provides
    @Singleton
    fun providesClient(apiKeyInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
//        добавляем наш интерцептор для добавления ключа
            .addInterceptor(apiKeyInterceptor)
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