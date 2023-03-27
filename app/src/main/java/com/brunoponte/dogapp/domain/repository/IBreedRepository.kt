package com.brunoponte.dogapp.domain.repository

import com.brunoponte.dogapp.domain.models.Breed
import com.brunoponte.dogapp.presentation.breedList.SortMode

interface IBreedRepository {
    suspend fun getBreeds(pageSize: Int, page: Int, order: SortMode) : List<Breed>

    suspend fun searchBreeds(query: String) : List<Breed>

    suspend fun getBreed(id: Int) : Breed?
}