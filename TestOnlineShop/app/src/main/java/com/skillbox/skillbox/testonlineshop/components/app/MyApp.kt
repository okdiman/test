package com.skillbox.skillbox.testonlineshop.components.app

import android.app.Application
import com.skillbox.skillbox.testonlineshop.R
import dagger.hilt.android.HiltAndroidApp
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("font/markpro.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
}