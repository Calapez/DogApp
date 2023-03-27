package com.brunoponte.dogapp.di

import com.brunoponte.dogapp.data.BreedRemote
import com.brunoponte.dogapp.network.BreedRemoteImp
import com.brunoponte.dogapp.network.IRequestService
import com.brunoponte.dogapp.network.Util
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
            .baseUrl(Util.dogApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IRequestService::class.java)
    }

    @Provides
    @Singleton
    fun provideBreedRemote(breedRemote: BreedRemoteImp): BreedRemote {
        return breedRemote
    }
}