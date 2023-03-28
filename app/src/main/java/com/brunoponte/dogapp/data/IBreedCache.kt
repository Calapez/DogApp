package com.brunoponte.dogapp.data

import com.brunoponte.dogapp.domain.models.Breed
import com.brunoponte.dogapp.presentation.breedList.SortMode

interface IBreedCache {
    suspend fun getBreeds(pageSize: Int, page: Int, order: SortMode): List<Breed>
    suspend fun searchBreeds(query: String): List<Breed>
    suspend fun getBreed(breedId: Int): Breed?
    suspend fun saveBreeds(breeds: List<Breed>)
    suspend fun isCached(): Boolean
    suspend fun setLastCacheTime(lastCache: Long)
    suspend fun isExpired(): Boolean
}
