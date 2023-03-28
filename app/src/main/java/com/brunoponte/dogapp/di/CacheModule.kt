package com.brunoponte.dogapp.di

import android.content.Context
import androidx.room.Room
import com.brunoponte.dogapp.cache.BreedCache
import com.brunoponte.dogapp.cache.daos.BreedDao
import com.brunoponte.dogapp.cache.database.AppDatabase
import com.brunoponte.dogapp.cache.utils.CachePreferencesHelper
import com.brunoponte.dogapp.data.IBreedCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBreedDao(db: AppDatabase): BreedDao {
        return db.breedDao()
    }

    @Provides
    @Singleton
    fun provideBreedCache(breedCache: BreedCache): IBreedCache {
        return breedCache
    }

    @Provides
    @Singleton
    fun providePreferenceHelper(@ApplicationContext context: Context): CachePreferencesHelper {
        return CachePreferencesHelper(context)
    }

}