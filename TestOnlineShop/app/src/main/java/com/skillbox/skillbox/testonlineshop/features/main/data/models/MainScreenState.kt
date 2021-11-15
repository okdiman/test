package com.skillbox.skillbox.testonlineshop.features.main.data.models

import com.skillbox.skillbox.testonlineshop.features.cart.data.models.CartDetailsWrapper

sealed class MainScreenState {
    object Loading : MainScreenState()
    object Error : MainScreenState()
    class Success(val result: CartDetailsWrapper) : MainScreenState()
}