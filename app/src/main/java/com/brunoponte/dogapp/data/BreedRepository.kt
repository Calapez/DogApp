package com.brunoponte.dogapp.data

import android.util.Log
import com.brunoponte.dogapp.cache.daos.BreedDao
import com.brunoponte.dogapp.cache.models.BreedEntityMapper
import com.brunoponte.dogapp.data.sources.BreedDataSourceFactory
import com.brunoponte.dogapp.domain.Response
import com.brunoponte.dogapp.domain.repository.IBreedRepository
import com.brunoponte.dogapp.domain.models.Breed
import com.brunoponte.dogapp.network.IRequestService
import com.brunoponte.dogapp.network.models.BreedDtoMapper
import com.brunoponte.dogapp.presentation.breedList.SortMode
import javax.inject.Inject

class BreedRepository
@Inject
constructor(
    private val dataSourceFactory: BreedDataSourceFactory
) : IBreedRepository {

    override suspend fun getBreeds(pageSize: Int, page: Int, order: SortMode): Response<List<Breed>> =
        try {
            // Get breeds
            val breeds = dataSourceFactory.getDataStore().getBreeds(pageSize, page, order)
            // Save breeds
            dataSourceFactory.getCacheDataSource().saveBreeds(breeds)
            Response.Success(breeds)
        } catch (exception: Exception) {
            // There was an issue
            exception.printStackTrace()
            Log.e("NetworkLayer", exception.message, exception)
            Response.Error(exception)
        }

    override suspend fun searchBreeds(query: String): Response<List<Breed>> =
        try {
            // Get breeds
            val breeds = dataSourceFactory.getDataStore().searchBreeds(query)
            // Insert into cache
            dataSourceFactory.getCacheDataSource().saveBreeds(breeds)
            Response.Success(breeds)
        } catch (exception: Exception) {
            // There was an issue
            exception.printStackTrace()
            Log.e("NetworkLayer", exception.message, exception)
            Response.Error(exception)
        }

    override suspend fun getBreed(id: Int): Response<Breed?> =
        try {
            Response.Success(dataSourceFactory.getCacheDataSource().getBreed(id))
        } catch (exception: Exception) {
            // There was an issue
            exception.printStackTrace()
            Log.e("CacheLayer", exception.message, exception)
            Response.Error(exception)
        }
}