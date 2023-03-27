package com.brunoponte.dogapp.data

import com.brunoponte.dogapp.domain.models.Breed
import com.brunoponte.dogapp.presentation.breedList.SortMode

interface BreedRemote {
    suspend fun getBreeds(pageSize: Int, page: Int, order: SortMode): List<Breed>
    suspend fun searchBreeds(query: String): List<Breed>
}
