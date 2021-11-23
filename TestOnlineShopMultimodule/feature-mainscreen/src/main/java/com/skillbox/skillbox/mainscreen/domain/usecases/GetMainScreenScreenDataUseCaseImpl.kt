package com.skillbox.skillbox.mainscreen.domain.usecases

import com.skillbox.skillbox.mainscreen.data.models.MainScreenResponseWrapper
import com.skillbox.skillbox.mainscreen.domain.repository.MainScreenRepository

class GetMainScreenScreenDataUseCaseImpl(private val repo: MainScreenRepository) : GetMainScreenDataUseCase {
    override var data: MainScreenResponseWrapper? = null
    override suspend fun invoke(): MainScreenResponseWrapper {
        data = repo.getMainScreenData()
        return data!!
    }
}