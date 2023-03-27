package com.brunoponte.dogapp.data.sources

import com.brunoponte.dogapp.data.BreedCache
import com.brunoponte.dogapp.data.BreedDataSource
import com.brunoponte.dogapp.domain.models.Breed
import com.brunoponte.dogapp.presentation.breedList.SortMode
import javax.inject.Inject

class BreedCacheDataSource @Inject constructor(
    private val breedCache: BreedCache
) : BreedDataSource {

    override suspend fun getBreeds(pageSize: Int, page: Int, order: SortMode): List<Breed> {
        return breedCache.getBreeds(pageSize, page, order)
    }

    override suspend fun searchBreeds(query: String): List<Breed> {
        return breedCache.searchBreeds(query)
    }

    override suspend fun getBreed(breedId: Int): Breed? {
        return breedCache.getBreed(breedId)
    }

    override suspend fun saveBreeds(breeds: List<Breed>) {
        breedCache.saveBreeds(breeds)
        breedCache.setLastCacheTime(System.currentTimeMillis())
    }

    override suspend fun isCached(): Boolean {
        return breedCache.isCached()
    }
}
