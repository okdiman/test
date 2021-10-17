package com.skillbox.skillbox.testonlineshop.domain.daggermodules

import com.skillbox.skillbox.testonlineshop.data.RepositoryImpl
import com.skillbox.skillbox.testonlineshop.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepo(impl: RepositoryImpl): Repository
}