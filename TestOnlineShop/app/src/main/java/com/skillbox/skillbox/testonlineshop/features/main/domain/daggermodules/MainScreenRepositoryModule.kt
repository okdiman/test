package com.skillbox.skillbox.testonlineshop.features.main.domain.daggermodules

import com.skillbox.skillbox.testonlineshop.features.main.data.repository.MainScreenRepositoryImpl
import com.skillbox.skillbox.testonlineshop.features.main.domain.repository.MainScreenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainScreenRepositoryModule {
    //    баиндим интерфейс репозитория
    @Binds
    abstract fun provideRepo(impl: MainScreenRepositoryImpl): MainScreenRepository
}