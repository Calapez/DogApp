package com.brunoponte.dogapp

import com.brunoponte.dogapp.data.cache.daos.BreedDao
import com.brunoponte.dogapp.data.cache.models.BreedEntity
import com.brunoponte.dogapp.data.cache.models.BreedEntityMapper
import com.brunoponte.dogapp.domain.models.Breed
import com.brunoponte.dogapp.data.network.IRequestService
import com.brunoponte.dogapp.data.network.models.BreedDto
import com.brunoponte.dogapp.data.network.models.BreedDtoMapper
import com.brunoponte.dogapp.data.repositories.BreedRepository
import com.brunoponte.dogapp.domain.SortMode
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class BreedRepositoryTest {

    private val breedDtos = listOf(
        BreedDto(
            1,
            "Alpha",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
        BreedDto(
            2,
            "Beta",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
        BreedDto(
            3,
            "Charlie",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
        BreedDto(
            4,
            "Delta",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
        BreedDto(
            5,
            "Echo",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
        BreedDto(
            6,
            "Foxtrot",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
        BreedDto(
            7,
            "Golf",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
        BreedDto(
            8,
            "Hotel",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
    )

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
        BreedEntity(
            6,
            "Foxtrot",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
        BreedEntity(
            7,
            "Golf",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
        BreedEntity(
            8,
            "Hotel",
            "Bred For",
            "Breed Group",
            "Life Span",
            "Temperament",
            "Reference Image Id",
            "Origin"
        ),
    )


    lateinit var repository: BreedRepository

    @Mock
    lateinit var requestService: IRequestService

    @Mock
    lateinit var breedDao: BreedDao

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = BreedRepository(requestService, breedDao)
    }

    @Test
    fun `get breeds from network asc`() {
        val pageSize = 2
        val page = 0
        val sortMode = SortMode.ASC
        val breedDomainModels = BreedDtoMapper.toDomainModelList(breedDtos.take(pageSize))

        runBlocking {
            Mockito.`when`(requestService.getBreeds(pageSize, page, "Asc")).thenReturn(breedDtos.take(pageSize))

            val returnedBreeds = repository.getBreeds(pageSize, page, sortMode)

            assertEquals(breedDomainModels, returnedBreeds)
        }
    }

    @Test
    fun `get breeds from network desc`() {
        val pageSize = 2
        val page = 0
        val sortMode = SortMode.DESC
        val breedDomainModels = BreedDtoMapper.toDomainModelList(breedDtos.takeLast(pageSize).asReversed())

        runBlocking {
            Mockito.`when`(requestService.getBreeds(pageSize, page, "Desc")).thenReturn(breedDtos.takeLast(pageSize).asReversed())

            val returnedBreeds = repository.getBreeds(pageSize, page, sortMode)

            assertEquals(breedDomainModels, returnedBreeds)
        }
    }

    @Test(expected = Exception::class)
    fun `get breeds offline asc`() {
        val pageSize = 2
        val page = 0
        val sortMode = SortMode.ASC
        val breedDomainModels = BreedDtoMapper.toDomainModelList(breedDtos.take(pageSize))

        runBlocking {
            Mockito.`when`(requestService.getBreeds(pageSize, page, "Asc")).thenThrow(Exception("Network request failed."))
            Mockito.`when`(breedDao.getBreedsAsc(pageSize, page)).thenReturn(breedEntities.take(pageSize))

            val returnedBreeds = repository.getBreeds(pageSize, page, sortMode)

            assertEquals(breedDomainModels, returnedBreeds)
        }
    }

    @Test(expected = Exception::class)
    fun `get breeds offline desc`() {
        val pageSize = 2
        val page = 0
        val sortMode = SortMode.DESC
        val breedDomainModels = BreedDtoMapper.toDomainModelList(breedDtos.take(pageSize))

        runBlocking {
            Mockito.`when`(requestService.getBreeds(pageSize, page, "Desc")).thenThrow(Exception("Network request failed."))
            Mockito.`when`(breedDao.getBreedsDesc(pageSize, page)).thenReturn(breedEntities.take(pageSize))

            val returnedBreeds = repository.getBreeds(pageSize, page, sortMode)

            assertEquals(breedDomainModels, returnedBreeds)
        }
    }

    @Test
    fun `get breeds empty from network`() {
        val pageSize = 2
        val page = 0
        val sortMode = SortMode.ASC

        runBlocking {
            Mockito.`when`(requestService.getBreeds(pageSize, page, "Asc")).thenReturn(listOf())

            val returnedRepos = repository.getBreeds(pageSize, page, sortMode)

            assertEquals(listOf<Breed>(), returnedRepos)
        }
    }

    @Test
    fun `get breeds empty offline`() {
        val pageSize = 2
        val page = 0
        val sortMode = SortMode.ASC

        runBlocking {
            Mockito.`when`(requestService.getBreeds(pageSize, page, "Asc")).thenReturn(listOf())
            Mockito.`when`(breedDao.getBreedsAsc(pageSize, page)).thenReturn(listOf())

            val returnedRepos = repository.getBreeds(pageSize, page, sortMode)

            assertEquals(listOf<Breed>(), returnedRepos)
        }
    }

    @Test
    fun `get breed from cache`() {
        val breedEntity = breedEntities[0]
        val breedDomainModel = BreedEntityMapper.mapToDomainModel(breedEntity)

        runBlocking {
            Mockito.`when`(breedDao.getBreed(breedEntity.id)).thenReturn(breedEntity)

            val returnedBreed = repository.getBreed(breedEntity.id)

            assertEquals(breedDomainModel, returnedBreed)
        }
    }

    @Test
    fun `get breed from cache not exists`() {
        runBlocking {
            Mockito.`when`(breedDao.getBreed(1)).thenReturn(null)

            val returnedBreed = repository.getBreed(1)

            assertEquals(null, returnedBreed)
        }
    }

}