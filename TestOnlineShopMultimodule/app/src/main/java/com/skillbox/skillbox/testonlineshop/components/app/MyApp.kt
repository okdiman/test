package com.skillbox.skillbox.testonlineshop.components.app

import android.app.Application
import com.skillbox.skillbox.testonlineshop.components.di.cartScreenModule
import com.skillbox.skillbox.testonlineshop.components.di.detailsScreenModule
import com.skillbox.skillbox.testonlineshop.components.di.generalModule
import com.skillbox.skillbox.testonlineshop.components.di.mainScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        подключаем Koin
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApp)
            modules(listOf(cartScreenModule, generalModule, detailsScreenModule, mainScreenModule))
        }
    }
}