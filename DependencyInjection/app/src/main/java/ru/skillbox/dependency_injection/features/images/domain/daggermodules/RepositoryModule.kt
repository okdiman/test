package ru.skillbox.dependency_injection.features.images.domain.daggermodules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.skillbox.dependency_injection.features.images.data.repository.ImagesRepositoryImpl
import ru.skillbox.dependency_injection.features.images.domain.repository.Repository


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRepository(impl: ImagesRepositoryImpl): Repository
}