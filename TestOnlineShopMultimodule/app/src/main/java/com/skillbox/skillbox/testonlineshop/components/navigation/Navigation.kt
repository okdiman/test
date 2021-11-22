package com.skillbox.skillbox.testonlineshop.components.navigation

import android.content.Context
import android.content.Intent
import com.skillbox.skillbox.cartscreen.presentation.CartScreenActivity
import com.skillbox.skillbox.core.navigation.INavigation
import com.skillbox.skillbox.detailsscreen.DetailsScreenActivity
import com.skillbox.skillbox.mainscreen.presentation.MainScreenActivity

class Navigation(private val context: Context) : INavigation {

    override fun getMainScreen(): Intent {
        return Intent(context, MainScreenActivity::class.java)
    }

    override fun getDetailsScreen(): Intent {
        return Intent(context, DetailsScreenActivity::class.java)
    }

    override fun getCartScreen(): Intent {
        return Intent(context, CartScreenActivity::class.java)
    }
}