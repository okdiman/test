package com.skillbox.skillbox.testonlineshop.components.di


import com.skillbox.skillbox.detailsscreen.data.network.DetailApi
import com.skillbox.skillbox.detailsscreen.data.repository.DetailRepositoryImpl
import com.skillbox.skillbox.detailsscreen.domain.repository.DetailRepository
import com.skillbox.skillbox.detailsscreen.presentation.screens.viewmodel.DetailsFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val detailsScreenModule = module {
    single<DetailApi> {
        val retrofit = get<Retrofit>(named("retrofit"))
        retrofit.create()
    }

    single<DetailRepository> {
        DetailRepositoryImpl(get())
    }

    viewModel {
        DetailsFragmentViewModel(get())
    }
}