package com.brunoponte.dogapp.data

import android.util.Log
import com.brunoponte.dogapp.data.sources.BreedDataSourceFactory
import com.brunoponte.dogapp.domain.Page
import com.brunoponte.dogapp.domain.Response
import com.brunoponte.dogapp.domain.repository.IBreedRepository
import com.brunoponte.dogapp.domain.models.Breed
import javax.inject.Inject

class BreedRepository
@Inject
constructor(
    private val dataSourceFactory: BreedDataSourceFactory
) : IBreedRepository {
    override suspend fun getBreeds(page: Page): Response<List<Breed>> =
        try {
            val isCached = dataSourceFactory.getCacheDataSource().isPageCached(page)
            // Get breeds from the proper source
            val breeds = dataSourceFactory.getDataStore(isCached).getBreeds(page)
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
            val breeds = dataSourceFactory.getCacheDataSource().searchBreeds(query)
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