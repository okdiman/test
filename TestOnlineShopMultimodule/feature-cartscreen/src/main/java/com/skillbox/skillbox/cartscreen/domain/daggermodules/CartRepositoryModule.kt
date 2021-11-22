package com.skillbox.skillbox.cartscreen.domain.daggermodules

import com.skillbox.skillbox.cartscreen.data.repository.CartRepositoryImpl
import com.skillbox.skillbox.cartscreen.domain.repository.CartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CartRepositoryModule {
    //    баиндим интерфейс репозитория
    @Binds
    abstract fun provideRepo(impl: CartRepositoryImpl): CartRepository
}
