package com.brunoponte.dogapp.data.sources

import com.brunoponte.dogapp.data.BreedCache
import com.brunoponte.dogapp.data.BreedDataSource
import javax.inject.Inject

open class BreedDataSourceFactory
@Inject
constructor(
    private val breedCache: BreedCache,
    private val cacheDataSource: BreedCacheDataSource,
    private val remoteDataSource: BreedRemoteDataSource
) {
    // TODO Use this and maker other methods private.
    open suspend fun getDataStore(): BreedDataSource {
        return if (breedCache.isCached() && !breedCache.isExpired()) {
            getCacheDataSource()
        } else {
            getRemoteDataSource()
        }
    }

    fun getRemoteDataSource(): BreedDataSource {
        return remoteDataSource
    }

    fun getCacheDataSource(): BreedDataSource {
        return cacheDataSource
    }
}
