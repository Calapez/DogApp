package com.brunoponte.dogapp.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brunoponte.dogapp.data.cache.models.BreedEntity

@Dao
interface BreedDao {

    @Query("""
        SELECT * FROM breeds 
        ORDER BY name ASC
        LIMIT :pageSize 
        OFFSET (:pageSize * (:page))
    """)
    suspend fun getBreedsAsc(pageSize: Int, page: Int): List<BreedEntity>

    @Query("""
        SELECT * FROM breeds 
        ORDER BY name DESC
        LIMIT :pageSize 
        OFFSET (:pageSize * (:page))
    """)
    suspend fun getBreedsDesc(pageSize: Int, page: Int): List<BreedEntity>

    @Query("""
        SELECT * FROM breeds 
        WHERE name LIKE '%' || :query || '%'
        ORDER BY name ASC""")
    suspend fun searchBreeds(query: String): List<BreedEntity>

    @Query("SELECT * FROM breeds WHERE id = :breedId")
    suspend fun getBreed(breedId: Int): BreedEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreeds(repos: List<BreedEntity>): LongArray

    // TODO Delete? No longer used
    @Query("SELECT (SELECT COUNT(*) FROM breeds) == 0")
    suspend fun isEmpty(): Boolean

    @Query("DELETE FROM breeds")
    suspend fun nukeTable()

}