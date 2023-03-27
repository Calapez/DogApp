package com.brunoponte.dogapp.di

import com.brunoponte.dogapp.cache.daos.BreedDao
import com.brunoponte.dogapp.network.IRequestService
import com.brunoponte.dogapp.data.BreedRepository
import com.brunoponte.dogapp.domain.repository.IBreedRepository
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
    fun provideBreedRepository(requestService: IRequestService, breedDao: BreedDao) : IBreedRepository {
        return BreedRepository(requestService, breedDao)
    }
}