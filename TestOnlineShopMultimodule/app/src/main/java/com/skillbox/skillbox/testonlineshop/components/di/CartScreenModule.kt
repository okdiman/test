package com.skillbox.skillbox.testonlineshop.components.di


import com.skillbox.skillbox.cartscreen.data.network.CartApi
import com.skillbox.skillbox.cartscreen.data.repository.CartRepositoryImpl
import com.skillbox.skillbox.cartscreen.domain.repository.CartRepository
import com.skillbox.skillbox.cartscreen.presentation.screens.viewmodel.CartFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val cartScreenModule = module {

    single<CartApi> {
        val retrofit = get<Retrofit>(named("retrofit"))
        retrofit.create()
    }

    single<CartRepository> {
        CartRepositoryImpl(get())
    }

    viewModel {
        CartFragmentViewModel(get())
    }
}