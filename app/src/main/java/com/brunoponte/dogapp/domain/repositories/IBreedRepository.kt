package com.brunoponte.dogapp.domain.repositories

import com.brunoponte.dogapp.domain.Page
import com.brunoponte.dogapp.domain.Response
import com.brunoponte.dogapp.domain.models.Breed

interface IBreedRepository {
    suspend fun getBreeds(page: Page) : Response<List<Breed>>

    suspend fun searchBreeds(query: String) : Response<List<Breed>>

    suspend fun getBreed(id: Int) : Response<Breed?>
}