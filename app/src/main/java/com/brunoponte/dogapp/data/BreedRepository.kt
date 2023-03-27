package com.brunoponte.dogapp.data

import com.brunoponte.dogapp.cache.daos.BreedDao
import com.brunoponte.dogapp.cache.models.BreedEntityMapper
import com.brunoponte.dogapp.domain.repository.IBreedRepository
import com.brunoponte.dogapp.domain.models.Breed
import com.brunoponte.dogapp.network.IRequestService
import com.brunoponte.dogapp.network.models.BreedDtoMapper
import com.brunoponte.dogapp.presentation.breedList.SortMode

class BreedRepository(
    private val requestService: IRequestService,
    private val breedDao: BreedDao
) : IBreedRepository {

    override suspend fun getBreeds(pageSize: Int, page: Int, order: SortMode): List<Breed> {
        try {
            val breeds = getBreedsFromNetwork(pageSize, page, getSortModeString(order))
            // Insert into cache
            breedDao.insertBreeds(BreedEntityMapper.toEntityList(breeds))
            return breeds
        } catch (e: Exception) {
            // There was an issue
            e.printStackTrace()
        }

        // If any error occurred, return the breeds stored in the cache
        val cachedBreeds = when(order) {
            SortMode.ASC -> breedDao.getBreedsAsc(pageSize = pageSize, page = page)
            SortMode.DESC -> breedDao.getBreedsDesc(pageSize = pageSize, page = page)
        }
        return BreedEntityMapper.toDomainModelList(cachedBreeds)
    }

    override suspend fun searchBreeds(query: String): List<Breed> {
        try {
            val breeds = searchBreedsFromNetwork(query)
            // Insert into cache
            breedDao.insertBreeds(BreedEntityMapper.toEntityList(breeds))
            return breeds
        } catch (e: Exception) {
            // There was an issue
            e.printStackTrace()
        }

        // If any error occurred, return the breeds stored in the cache
        val cachedBreeds = breedDao.searchBreeds(query = query)
        return BreedEntityMapper.toDomainModelList(cachedBreeds)
    }

    override suspend fun getBreed(id: Int): Breed? {
        val breedEntity = breedDao.getBreed(id)
        return breedEntity?.let { BreedEntityMapper.mapToDomainModel(it) }
    }

    private suspend fun getBreedsFromNetwork(pageSize: Int, page: Int, order: String) =
        BreedDtoMapper.toDomainModelList(
            requestService.getBreeds(pageSize, page, order)
        )

    private suspend fun searchBreedsFromNetwork(query: String) =
        BreedDtoMapper.toDomainModelList(
            requestService.searchBreeds(query)
        )

    private fun getSortModeString(sortMode: SortMode) = when (sortMode) {
        SortMode.ASC -> "Asc"
        SortMode.DESC -> "Desc"
    }
}