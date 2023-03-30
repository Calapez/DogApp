package com.brunoponte.dogapp.data.cache

import com.brunoponte.dogapp.domain.Page
import com.brunoponte.dogapp.domain.models.Breed

interface IBreedCache {
    suspend fun getBreeds(page: Page): List<Breed>
    suspend fun searchBreeds(query: String): List<Breed>
    suspend fun getBreed(breedId: Int): Breed?
    suspend fun saveBreeds(breeds: List<Breed>)
    suspend fun isPageCached(page: Page): Boolean
    suspend fun setLastCacheTime(lastCache: Long)
    suspend fun isExpired(): Boolean
}
