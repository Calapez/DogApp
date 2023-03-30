package com.brunoponte.dogapp.di

import com.brunoponte.dogapp.data.repositories.BreedRepository
import com.brunoponte.dogapp.domain.repositories.IBreedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The binding for IBreedRepository is on its own module so that we can replace it easily in tests.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideBreedRepository(
        breedRepository: BreedRepository,
    ) : IBreedRepository {
        return breedRepository
    }
}