package com.skillbox.skillbox.testonlineshop.components.app

import android.app.Application
import com.skillbox.skillbox.core.navigation.INavigation
import com.skillbox.skillbox.core.navigation.INavigationProvider
import com.skillbox.skillbox.testonlineshop.components.di.cartScreenModule
import com.skillbox.skillbox.testonlineshop.components.di.detailsScreenModule
import com.skillbox.skillbox.testonlineshop.components.di.generalModule
import com.skillbox.skillbox.testonlineshop.components.di.mainScreenModule
import com.skillbox.skillbox.testonlineshop.components.navigation.Navigation
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application(), INavigationProvider {
    lateinit var navigation: Navigation
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApp)
            modules(listOf(cartScreenModule, generalModule, detailsScreenModule, mainScreenModule))
        }
        navigation = Navigation(this)
    }

    override fun getAppNavigation(): INavigation {
        return navigation
    }
}