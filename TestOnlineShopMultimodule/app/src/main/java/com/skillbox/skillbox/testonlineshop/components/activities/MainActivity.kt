package com.skillbox.skillbox.testonlineshop.components.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.skillbox.core.navigation.INavigation
import com.skillbox.skillbox.core.navigation.INavigationProvider
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.components.app.MyApp
import com.skillbox.skillbox.testonlineshop.components.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val provider = object : INavigationProvider {
            override fun getAppNavigation(): INavigation {
                return Navigation(applicationContext)
            }
        }
        val nav = provider.getAppNavigation()
        startActivity(nav.getMainScreen())
    }
}