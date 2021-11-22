package com.skillbox.skillbox.mainscreen.data.models


sealed class MainScreenState {
    object Loading : MainScreenState()
    object Error : MainScreenState()
    class Success(val result: CartDetailsWrapper) : MainScreenState()
}