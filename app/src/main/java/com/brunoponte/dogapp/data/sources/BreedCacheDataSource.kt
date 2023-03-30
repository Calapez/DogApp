package com.brunoponte.dogapp.data.sources

import com.brunoponte.dogapp.data.IBreedCache
import com.brunoponte.dogapp.data.IBreedDataSource
import com.brunoponte.dogapp.domain.Page
import com.brunoponte.dogapp.domain.models.Breed
import javax.inject.Inject

class BreedCacheDataSource @Inject constructor(
    private val breedCache: IBreedCache
) : IBreedDataSource {

    override suspend fun getBreeds(page: Page): List<Breed> {
        return breedCache.getBreeds(page)
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

    override suspend fun isPageCached(page: Page): Boolean {
        return breedCache.isPageCached(page)
    }
}
