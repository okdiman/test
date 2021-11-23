package com.skillbox.skillbox.mainscreen.domain.usecases

import com.skillbox.skillbox.mainscreen.data.models.MainScreenResponseWrapper

interface GetMainScreenDataUseCase {
    var data: MainScreenResponseWrapper?
    suspend fun invoke(): MainScreenResponseWrapper
}