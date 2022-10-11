package com.brunoponte.dogapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.brunoponte.dogapp.cache.daos.BreedDao
import com.brunoponte.dogapp.cache.database.AppDatabase
import com.brunoponte.dogapp.cache.entities.BreedEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BreedDaoTest {
    private val breedEntities = listOf(
        BreedEntity(
            1,
            "Alpha",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
        BreedEntity(
            2,
            "Beta",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
        BreedEntity(
            3,
            "Charlie",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
        BreedEntity(
            4,
            "Delta",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
        BreedEntity(
            5,
            "Echo",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
    )

    private lateinit var breedDao: BreedDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        breedDao = db.breedDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun getBreedsFirstPageAscTest() {
        val pageSize = 2
        val page = 0
        val breedsToReturn = breedEntities.take(pageSize)

        runBlocking {
            breedDao.nukeTable()
            breedDao.insertBreeds(breedEntities)

            val returnedEntities = breedDao.getBreedsAsc(pageSize, page)

            assertEquals(breedsToReturn, returnedEntities)
        }
    }

    @Test
    @Throws(Exception::class)
    fun getBreedsFirstPageDescTest() {
        val pageSize = 2
        val page = 0
        val breedsToReturn = breedEntities.takeLast(pageSize).asReversed()

        runBlocking {
            breedDao.nukeTable()
            breedDao.insertBreeds(breedEntities)

            val returnedEntities = breedDao.getBreedsDesc(pageSize, page)

            assertEquals(breedsToReturn, returnedEntities)
        }
    }

    @Test
    @Throws(Exception::class)
    fun getBreedsSecondPageTest() {
        val pageSize = 2
        val page = 1
        val breedsToReturn = breedEntities.slice(setOf(2,3))

        runBlocking {
            breedDao.nukeTable()
            breedDao.insertBreeds(breedEntities)

            val returnedEntities = breedDao.getBreedsAsc(pageSize, page)

            assertEquals(breedsToReturn, returnedEntities)
        }
    }

    @Test
    @Throws(Exception::class)
    fun getBreedsThirdPageTest() {
        val pageSize = 2
        val page = 2
        val breedsToReturn = breedEntities.slice(setOf(4))

        runBlocking {
            breedDao.nukeTable()
            breedDao.insertBreeds(breedEntities)

            val returnedEntities = breedDao.getBreedsAsc(pageSize, page)

            assertEquals(breedsToReturn, returnedEntities)
        }
    }
}
