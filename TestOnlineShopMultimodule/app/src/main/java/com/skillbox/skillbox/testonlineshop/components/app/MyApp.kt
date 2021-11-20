package com.skillbox.skillbox.testonlineshop.components.app

import android.app.Application
import com.skillbox.skillbox.core.navigation.INavigation
import com.skillbox.skillbox.core.navigation.INavigationProvider
import com.skillbox.skillbox.testonlineshop.components.navigation.Navigation
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(), INavigationProvider {
    lateinit var navigation: Navigation
    override fun onCreate() {
        super.onCreate()
        navigation = Navigation(this)
    }

    override fun getAppNavigation(): INavigation {
        return navigation
    }
}