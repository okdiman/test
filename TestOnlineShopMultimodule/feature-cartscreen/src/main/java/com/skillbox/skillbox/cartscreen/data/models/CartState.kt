package com.skillbox.skillbox.cartscreen.data.models

sealed class CartState {
    object Error : CartState()
    object Loading : CartState()
    class Success(val result: CartDetailsWrapper) : CartState()
}


