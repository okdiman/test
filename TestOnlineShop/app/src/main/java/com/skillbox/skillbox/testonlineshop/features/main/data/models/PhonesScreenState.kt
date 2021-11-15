package com.skillbox.skillbox.testonlineshop.features.main.data.models


sealed class PhonesScreenState {
    object Loading : PhonesScreenState()
    object Error : PhonesScreenState()
    class Success(val result: MainScreenResponseWrapper) : PhonesScreenState()
}