package com.brunoponte.dogapp.data

import android.util.Log
import com.brunoponte.dogapp.cache.daos.BreedDao
import com.brunoponte.dogapp.cache.models.BreedEntityMapper
import com.brunoponte.dogapp.domain.Response
import com.brunoponte.dogapp.domain.repository.IBreedRepository
import com.brunoponte.dogapp.domain.models.Breed
import com.brunoponte.dogapp.network.IRequestService
import com.brunoponte.dogapp.network.models.BreedDtoMapper
import com.brunoponte.dogapp.presentation.breedList.SortMode

class BreedRepository(
    private val requestService: IRequestService,
    private val breedDao: BreedDao
) : IBreedRepository {

    override suspend fun getBreeds(pageSize: Int, page: Int, order: SortMode): Response<List<Breed>> {
        val networkResponse =
            try {
                val breeds = getBreedsFromNetwork(pageSize, page, getSortModeString(order))
                // Insert into cache
                breedDao.insertBreeds(BreedEntityMapper.toEntityList(breeds))
                Response.Success(breeds)
            } catch (exception: Exception) {
                // There was an issue
                exception.printStackTrace()
                Log.e("NetworkLayer", exception.message, exception)
                Response.Error(exception)
            }

        if (networkResponse is Response.Success || breedDao.isEmpty()) {
            return networkResponse
        }

        // If any error occurred, return the breeds stored in the cache
        val cachedBreeds = when(order) {
            SortMode.ASC -> breedDao.getBreedsAsc(pageSize = pageSize, page = page)
            SortMode.DESC -> breedDao.getBreedsDesc(pageSize = pageSize, page = page)
        }
        return Response.Success(BreedEntityMapper.toDomainModelList(cachedBreeds))
    }

    override suspend fun searchBreeds(query: String): Response<List<Breed>> {
        val networkResponse =
            try {
                val breeds = searchBreedsFromNetwork(query)
                // Insert into cache
                breedDao.insertBreeds(BreedEntityMapper.toEntityList(breeds))
                Response.Success(breeds)
            } catch (exception: Exception) {
                // There was an issue
                exception.printStackTrace()
                Log.e("NetworkLayer", exception.message, exception)
                Response.Error(exception)
            }

        if (networkResponse is Response.Success || breedDao.isEmpty()) {
            return networkResponse
        }

        // If any error occurred, return the breeds stored in the cache
        val cachedBreeds = breedDao.searchBreeds(query = query)
        return Response.Success(BreedEntityMapper.toDomainModelList(cachedBreeds))
    }

    override suspend fun getBreed(id: Int): Response<Breed?> =
        try {
            val breedEntity = breedDao.getBreed(id)
            Response.Success(breedEntity?.let { BreedEntityMapper.mapToDomainModel(it) })
        } catch (exception: Exception) {
            // There was an issue
            exception.printStackTrace()
            Log.e("CacheLayer", exception.message, exception)
            Response.Error(exception)
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