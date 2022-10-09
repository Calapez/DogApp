package com.brunoponte.dogapp.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brunoponte.dogapp.cache.entities.BreedEntity

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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBreeds(repos: List<BreedEntity>): LongArray

    @Query("DELETE FROM breeds")
    suspend fun nukeTable()

}