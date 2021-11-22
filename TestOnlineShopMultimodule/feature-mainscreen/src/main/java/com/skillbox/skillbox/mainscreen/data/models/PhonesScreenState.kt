package com.skillbox.skillbox.mainscreen.data.models


sealed class PhonesScreenState {
    object Loading : PhonesScreenState()
    object Error : PhonesScreenState()
    class Success(val result: MainScreenResponseWrapper) : PhonesScreenState()
}