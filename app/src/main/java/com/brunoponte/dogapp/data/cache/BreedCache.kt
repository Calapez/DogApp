package com.brunoponte.dogapp.data.cache

import com.brunoponte.dogapp.data.cache.daos.BreedDao
import com.brunoponte.dogapp.data.cache.models.BreedEntityMapper
import com.brunoponte.dogapp.data.cache.utils.CachePreferencesHelper
import com.brunoponte.dogapp.domain.Page
import com.brunoponte.dogapp.domain.models.Breed
import com.brunoponte.dogapp.domain.SortMode
import javax.inject.Inject

class BreedCache @Inject constructor(
    private val breedDao: BreedDao,
    private val preferencesHelper: CachePreferencesHelper
) : IBreedCache {

    override suspend fun getBreeds(page: Page): List<Breed> {
        val breeds = when(page.order) {
            SortMode.ASC -> breedDao.getBreedsAsc(page.size, page.page)
            SortMode.DESC -> breedDao.getBreedsDesc(page.size, page.page)
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

    override suspend fun isPageCached(page: Page): Boolean {
        return getBreeds(page).isNotEmpty()
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
