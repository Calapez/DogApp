package com.brunoponte.dogapp.di

import com.brunoponte.dogapp.cache.daos.BreedDao
import com.brunoponte.dogapp.network.IRequestService
import com.brunoponte.dogapp.repository.BreedRepository
import com.brunoponte.dogapp.repository.IBreedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideBreedRepository(requestService: IRequestService, breedDao: BreedDao) : IBreedRepository {
        return BreedRepository(requestService, breedDao)
    }
}