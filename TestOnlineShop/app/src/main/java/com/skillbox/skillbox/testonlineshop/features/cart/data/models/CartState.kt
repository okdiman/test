package com.skillbox.skillbox.testonlineshop.features.cart.data.models

sealed class CartState {
    object Error : CartState()
    object Loading : CartState()
    class Success(val result: CartDetailsWrapper) : CartState()
}


