package com.skillbox.skillbox.testonlineshop.features.main.domain.daggermodules

import android.util.Log
import com.skillbox.skillbox.testonlineshop.core.utils.apiKey
import com.skillbox.skillbox.testonlineshop.features.main.data.network.MainScreenApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainScreenNetworkModule {

    //    создаем квалификатор для пометки данных mainScreen
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainScreenRetrofitData

    //    провайдим кастомный интерцептор для добавления api key в запрос
    @MainScreenRetrofitData
    @Provides
    fun providesApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            //получаем оригинальный запрос
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

    //    провайдим синглтон okHttpClient
    @MainScreenRetrofitData
    @Provides
    @Singleton
    fun providesClient(@MainScreenRetrofitData apiKeyInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
//        добавляем наш интерцептор для добавления ключа
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    //    провайдим синглтон Retrofit
    @MainScreenRetrofitData
    @Provides
    @Singleton
    fun providesRetrofit(@MainScreenRetrofitData client: OkHttpClient): Retrofit {
        //    создаем объект ретрофита
        return Retrofit.Builder()
            .baseUrl("https://db2021ecom-edca.restdb.io/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    //    провайдим наш MainScreenApi
    @Provides
    fun providesApi(@MainScreenRetrofitData retrofit: Retrofit): MainScreenApi {
        //    связываем наш Api интерфейс и ретрофит
        return retrofit.create()
    }
}