package com.skillbox.skillbox.testonlineshop.features.cart.domain.daggermodules

import com.skillbox.skillbox.testonlineshop.features.cart.data.repository.CartRepositoryImpl
import com.skillbox.skillbox.testonlineshop.features.cart.domain.repository.CartRepository
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
