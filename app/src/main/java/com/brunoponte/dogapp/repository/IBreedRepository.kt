package com.brunoponte.dogapp.repository

import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.ui.breedList.SortMode

interface IBreedRepository {
    suspend fun getBreeds(pageSize: Int, page: Int, order: SortMode) : List<Breed>

    suspend fun searchBreeds(query: String) : List<Breed>

    suspend fun getBreed(id: Int) : Breed?
}