package com.brunoponte.dogapp.cache

import com.brunoponte.dogapp.cache.daos.BreedDao
import com.brunoponte.dogapp.cache.models.BreedEntityMapper
import com.brunoponte.dogapp.cache.utils.CachePreferencesHelper
import com.brunoponte.dogapp.data.IBreedCache
import com.brunoponte.dogapp.domain.models.Breed
import com.brunoponte.dogapp.presentation.breedList.SortMode
import javax.inject.Inject

class BreedCache @Inject constructor(
    private val breedDao: BreedDao,
    private val preferencesHelper: CachePreferencesHelper
) : IBreedCache {

    override suspend fun getBreeds(pageSize: Int, page: Int, order: SortMode): List<Breed> {
        val breeds = when(order) {
            SortMode.ASC -> breedDao.getBreedsAsc(pageSize = pageSize, page = page)
            SortMode.DESC -> breedDao.getBreedsDesc(pageSize = pageSize, page = page)
        }
        return BreedEntityMapper.toDomainModelList(breeds)
    }

    override suspend fun searchBreeds(query: String): List<Breed> {
        val breeds = breedDao.searchBreeds(query)
        return BreedEntityMapper.toDomainModelList(breeds)
    }

    override suspend fun getBreed(breedId: Int): Breed? {
        return breedDao.getBreed(breedId)?.let { BreedEntityMapper.mapToDomainModel(it) }
    }

    override suspend fun saveBreeds(breeds: List<Breed>) {
        breedDao.insertBreeds(BreedEntityMapper.toEntityList(breeds))
    }

    override suspend fun isCached(): Boolean {
        return !breedDao.isEmpty()
    }

    override suspend fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    override suspend fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

    companion object {
        /**
         * Expiration time set to 5 minutes
         */
        const val EXPIRATION_TIME = (60 * 5 * 1000).toLong()
    }
}
