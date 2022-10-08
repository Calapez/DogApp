package com.brunoponte.dogapp.di

import androidx.room.Room
import com.brunoponte.dogapp.App
import com.brunoponte.dogapp.cache.daos.BreedDao
import com.brunoponte.dogapp.cache.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideAppDatabase(app: App): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBreedDao(db: AppDatabase): BreedDao {
        return db.breedDao()
    }

}