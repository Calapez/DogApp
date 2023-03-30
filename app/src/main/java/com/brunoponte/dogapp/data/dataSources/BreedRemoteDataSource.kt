package com.brunoponte.dogapp.data.dataSources

import com.brunoponte.dogapp.data.network.IBreedRemote
import com.brunoponte.dogapp.domain.Page
import com.brunoponte.dogapp.domain.models.Breed
import javax.inject.Inject

class BreedRemoteDataSource
@Inject
constructor(
    private val breedRemote: IBreedRemote
) : IBreedDataSource {

    override suspend fun getBreeds(page: Page): List<Breed> {
        return breedRemote.getBreeds(page)
    }

    override suspend fun searchBreeds(query: String): List<Breed> {
        return breedRemote.searchBreeds(query)
    }

    override suspend fun getBreed(breedId: Int): Breed? {
        throw UnsupportedOperationException("Get character is not supported for RemoteDataSource.")
    }

    override suspend fun saveBreeds(breeds: List<Breed>) {
        throw UnsupportedOperationException("Save character is not supported for RemoteDataSource.")
    }

    override suspend fun isPageCached(page: Page): Boolean {
        throw UnsupportedOperationException("Cache is not supported for RemoteDataSource.")
    }
}
