package com.skillbox.skillbox.testonlineshop.features.general.domain.daggermodules

import com.skillbox.skillbox.testonlineshop.features.general.data.repository.RepositoryImpl
import com.skillbox.skillbox.testonlineshop.features.general.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    //    баиндим интерфейс репозитория
    @Binds
    abstract fun provideRepo(impl: RepositoryImpl): Repository
}