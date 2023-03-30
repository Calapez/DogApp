package com.brunoponte.dogapp.data

import com.brunoponte.dogapp.domain.Page
import com.brunoponte.dogapp.domain.models.Breed

interface IBreedDataSource {
    // Remote and cache
    suspend fun getBreeds(page: Page): List<Breed>
    suspend fun searchBreeds(query: String): List<Breed>
    suspend fun getBreed(breedId: Int): Breed?

    // Cache
    suspend fun saveBreeds(breeds: List<Breed>)
    suspend fun isPageCached(page: Page): Boolean
}
