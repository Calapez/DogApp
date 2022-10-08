package com.brunoponte.dogapp.repository

import com.brunoponte.dogapp.cache.daos.BreedDao
import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.network.IRequestService
import com.brunoponte.dogapp.network.dtos.BreedDtoMapper

class BreedRepository(
    private val requestService: IRequestService,
    private val breedDao: BreedDao
) : IBreedRepository {

    override suspend fun getBreeds(pageSize: Int, page: Int, order: String): List<Breed> {
        return try {
            getBreedsFromNetwork(pageSize, page, order)
        } catch (e: Exception) {
            // There was an issue
            e.printStackTrace()

            listOf()
        }
    }

    override suspend fun searchBreeds(query: String): List<Breed> {
        return try {
            searchBreedsFromNetwork(query)
        } catch (e: Exception) {
            // There was an issue
            e.printStackTrace()

            listOf()
        }
    }

    override suspend fun getBreed(id: Int): Breed? {
        return try {
            getBreedFromNetwork(id)
        } catch (e: Exception) {
            // There was an issue
            e.printStackTrace()
            null
        }
    }

    private suspend fun getBreedsFromNetwork(pageSize: Int, page: Int, order: String) =
        BreedDtoMapper.toDomainModelList(
            requestService.getBreeds(pageSize, page, order)
        )

    private suspend fun searchBreedsFromNetwork(query: String) =
        BreedDtoMapper.toDomainModelList(
            requestService.searchBreeds(query)
        )

    private suspend fun getBreedFromNetwork(id: Int) =
        BreedDtoMapper.mapToDomainModel(
            requestService.getBreed(id)
        )
}