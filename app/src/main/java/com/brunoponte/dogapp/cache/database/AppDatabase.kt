package com.brunoponte.dogapp.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brunoponte.dogapp.cache.daos.BreedDao
import com.brunoponte.dogapp.cache.models.BreedEntity

@Database(
    entities = [BreedEntity::class],
    version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun breedDao(): BreedDao

    companion object{
        const val DATABASE_NAME = "dog_app_db"
    }

}