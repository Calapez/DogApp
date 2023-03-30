package com.brunoponte.dogapp.di

import com.brunoponte.dogapp.repository.FakeBreedRepository
import com.brunoponte.dogapp.domain.repositories.IBreedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 * IBreedRepository binding to use in tests.
 *
 * Hilt will inject a [FakeBreedRepository] instead of a [BreedRepository].
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class TestBreedRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRepository(repo: FakeBreedRepository): IBreedRepository
}
