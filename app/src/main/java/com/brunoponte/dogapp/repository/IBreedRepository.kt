package com.brunoponte.dogapp.repository

import com.brunoponte.dogapp.domainModels.Breed

interface IBreedRepository {
    suspend fun getBreeds(pageSize: Int, page: Int, order: String) : List<Breed>

    suspend fun searchBreeds(query: String) : List<Breed>

    suspend fun getBreed(id: Int) : Breed?
}