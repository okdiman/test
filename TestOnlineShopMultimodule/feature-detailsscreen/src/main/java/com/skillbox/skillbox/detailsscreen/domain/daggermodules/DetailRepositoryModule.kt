package com.skillbox.skillbox.detailsscreen.domain.daggermodules

import com.skillbox.skillbox.detailsscreen.data.repository.DetailRepositoryImpl
import com.skillbox.skillbox.detailsscreen.domain.repository.DetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DetailRepositoryModule {
    //    баиндим интерфейс репозитория
    @Binds
    abstract fun provideRepo(impl: DetailRepositoryImpl): DetailRepository
}