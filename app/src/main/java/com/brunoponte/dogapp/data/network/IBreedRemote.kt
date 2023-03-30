package com.brunoponte.dogapp.data.network

import com.brunoponte.dogapp.domain.Page
import com.brunoponte.dogapp.domain.models.Breed

interface IBreedRemote {
    suspend fun getBreeds(page: Page): List<Breed>
    suspend fun searchBreeds(query: String): List<Breed>
}
