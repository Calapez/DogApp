package com.brunoponte.dogapp.data.sources

import com.brunoponte.dogapp.data.BreedDataSource
import com.brunoponte.dogapp.data.BreedRemote
import com.brunoponte.dogapp.domain.models.Breed
import com.brunoponte.dogapp.presentation.breedList.SortMode
import javax.inject.Inject

class BreedRemoteDataSource
@Inject
constructor(
    private val breedRemote: BreedRemote
) : BreedDataSource {

    override suspend fun getBreeds(pageSize: Int, page: Int, order: SortMode): List<Breed> {
        return breedRemote.getBreeds(pageSize, page, order)
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

    override suspend fun isCached(): Boolean {
        throw UnsupportedOperationException("Cache is not supported for RemoteDataSource.")
    }
}
