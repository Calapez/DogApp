package com.brunoponte.dogapp.di

import com.brunoponte.dogapp.data.network.IBreedRemote
import com.brunoponte.dogapp.data.network.BreedRemote
import com.brunoponte.dogapp.data.network.IRequestService
import com.brunoponte.dogapp.data.network.utils.Endpoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideRequestService() : IRequestService {
        return Retrofit.Builder()
            .baseUrl(Endpoints.dogApiEndpoint)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IRequestService::class.java)
    }

    @Provides
    @Singleton
    fun provideBreedRemote(breedRemote: BreedRemote): IBreedRemote {
        return breedRemote
    }
}