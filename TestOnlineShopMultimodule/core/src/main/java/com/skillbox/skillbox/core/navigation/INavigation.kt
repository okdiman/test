package com.skillbox.skillbox.core.navigation

import android.content.Intent

interface INavigation {
    fun getMainScreen(): Intent
    fun getDetailsScreen(): Intent
    fun getCartScreen(): Intent
}