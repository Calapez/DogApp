package com.brunoponte.dogapp.data.sources

import com.brunoponte.dogapp.data.IBreedCache
import com.brunoponte.dogapp.data.IBreedDataSource
import javax.inject.Inject

open class BreedDataSourceFactory
@Inject
constructor(
    private val breedCache: IBreedCache,
    private val cacheDataSource: BreedCacheDataSource,
    private val remoteDataSource: BreedRemoteDataSource
) {
    // TODO Use this and maker other methods private.
    open suspend fun getDataStore(): IBreedDataSource {
        return if (breedCache.isCached() && !breedCache.isExpired()) {
            getCacheDataSource()
        } else {
            getRemoteDataSource()
        }
    }

    fun getRemoteDataSource(): IBreedDataSource {
        return remoteDataSource
    }

    fun getCacheDataSource(): IBreedDataSource {
        return cacheDataSource
    }
}
