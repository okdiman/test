package com.skillbox.skillbox.testonlineshop.components.di

import com.skillbox.skillbox.mainscreen.data.network.MainScreenApi
import com.skillbox.skillbox.mainscreen.data.repository.MainScreenRepositoryImpl
import com.skillbox.skillbox.mainscreen.domain.repository.MainScreenRepository
import com.skillbox.skillbox.mainscreen.domain.usecases.GetMainDataUseCase
import com.skillbox.skillbox.mainscreen.domain.usecases.GetMainScreenDataUseCaseImpl
import com.skillbox.skillbox.mainscreen.presentation.screens.viewmodel.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

//модуль главного экрана
val mainScreenModule = module {
    single<MainScreenApi> {
        val retrofit = get<Retrofit>(named("retrofit"))
        retrofit.create()
    }

    single<MainScreenRepository> {
        MainScreenRepositoryImpl(get())
    }

    single <GetMainDataUseCase>{
        GetMainScreenDataUseCaseImpl(get())
    }

    viewModel {
        MainScreenViewModel(get(), get())
    }
}