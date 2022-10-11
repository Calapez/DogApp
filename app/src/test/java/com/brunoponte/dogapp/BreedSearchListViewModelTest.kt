package com.brunoponte.dogapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.repository.IBreedRepository
import com.brunoponte.dogapp.ui.breedList.BreedListViewModel
import com.brunoponte.dogapp.ui.breedList.SortMode
import com.brunoponte.dogapp.ui.breedSearchList.BreedSearchListViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class BreedSearchListViewModelTest {

    private val breeds = mutableListOf<Breed>()

    lateinit var viewModel: BreedSearchListViewModel

    @Mock
    lateinit var repository: IBreedRepository

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = BreedSearchListViewModel(repository, testDispatcher)

        breeds.addAll(listOf(
            Breed(
                1,
                "Alpha",
                "Bred For",
                "Breed Group",
                "Life Span",
                "Temperament",
                "Reference Image Id",
                "Origin"
            ),
            Breed(
                2,
                "Beta",
                "Bred For",
                "Breed Group",
                "Life Span",
                "Temperament",
                "Reference Image Id",
                "Origin"
            ),
            Breed(
                3,
                "Charlie",
                "Bred For",
                "Breed Group",
                "Life Span",
                "Temperament",
                "Reference Image Id",
                "Origin"
            ),
            Breed(
                4,
                "Delta",
                "Bred For",
                "Breed Group",
                "Life Span",
                "Temperament",
                "Reference Image Id",
                "Origin"
            ),
            Breed(
                5,
                "Echo",
                "Bred For",
                "Breed Group",
                "Life Span",
                "Temperament",
                "Reference Image Id",
                "Origin"
            ),
            Breed(
                6,
                "Foxtrot",
                "Bred For",
                "Breed Group",
                "Life Span",
                "Temperament",
                "Reference Image Id",
                "Origin"
            ),
            Breed(
                7,
                "Golf",
                "Bred For",
                "Breed Group",
                "Life Span",
                "Temperament",
                "Reference Image Id",
                "Origin"
            ),
            Breed(
                8,
                "Hotel",
                "Bred For",
                "Breed Group",
                "Life Span",
                "Temperament",
                "Reference Image Id",
                "Origin"
            ),
        ))
    }

    @Test
    fun `search empty query`() {
        val breedsToReturn = listOf<Breed>()

        runBlocking {
            Mockito.`when`(repository.searchBreeds(""))
                .thenReturn(breedsToReturn)

            viewModel.searchBreeds("")

            assertEquals(breedsToReturn, viewModel.breeds.value)
        }
    }

    @Test
    fun `search query no match`() {
        val breedsToReturn = listOf<Breed>()

        runBlocking {
            Mockito.`when`(repository.searchBreeds("Beagle"))
                .thenReturn(breedsToReturn)

            viewModel.searchBreeds("Beagle")

            assertEquals(breedsToReturn, viewModel.breeds.value)
        }
    }

    @Test
    fun `search query one match`() {
        val breedsToReturn = breeds.filter { it.name == "Alpha" }

        runBlocking {
            Mockito.`when`(repository.searchBreeds("Alpha"))
                .thenReturn(breedsToReturn)

            viewModel.searchBreeds("Alpha")

            assertEquals(breedsToReturn, viewModel.breeds.value)
        }
    }

    @Test
    fun `search query multiple matches`() {
        val breedsToReturn = breeds.filter { it.name?.contains("ta", ignoreCase = true) ?: false }

        runBlocking {
            Mockito.`when`(repository.searchBreeds("Ta"))
                .thenReturn(breedsToReturn)

            viewModel.searchBreeds("Ta")

            assertEquals(breedsToReturn, viewModel.breeds.value)
        }
    }
}