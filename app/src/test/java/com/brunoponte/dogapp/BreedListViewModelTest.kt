package com.brunoponte.dogapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.brunoponte.dogapp.domain.models.Breed
import com.brunoponte.dogapp.domain.repositories.IBreedRepository
import com.brunoponte.dogapp.presentation.breedList.BreedListViewModel
import com.brunoponte.dogapp.domain.SortMode
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
class BreedListViewModelTest {

    private val breeds = mutableListOf<Breed>()

    lateinit var viewModel: BreedListViewModel

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
        viewModel = BreedListViewModel(repository, testDispatcher)

        for (i in 1 .. 2*BreedListViewModel.PAGE_SIZE) {
            breeds.add(
                Breed(
                    i,
                    "Name",
                    "Bred For",
                    "Breed Group",
                    "Life Span",
                    "Temperament",
                    "Reference Image Id",
                    "Origin"
                )
            )
        }
    }

    @Test
    fun `get breeds first page`() {
        val breedsToReturn = breeds.take(BreedListViewModel.PAGE_SIZE)

        runBlocking {
            Mockito.`when`(repository.getBreeds(BreedListViewModel.PAGE_SIZE, 0, SortMode.ASC))
                .thenReturn(breedsToReturn)

            viewModel.getFirstBreeds()

            assertEquals(breedsToReturn, viewModel.breeds.value)
        }
    }

    @Test
    fun `get breeds second page`() {
        val scrollPosition = BreedListViewModel.PAGE_SIZE - 1
        val breedsToReturn1 = breeds.take(BreedListViewModel.PAGE_SIZE)
        val breedsToReturn2 = breeds.slice(scrollPosition + 1 .. scrollPosition + BreedListViewModel.PAGE_SIZE)

        runBlocking {
            Mockito.`when`(repository.getBreeds(BreedListViewModel.PAGE_SIZE, 0, SortMode.ASC))
                .thenReturn(breedsToReturn1)
            viewModel.getFirstBreeds()

            Mockito.`when`(repository.getBreeds(BreedListViewModel.PAGE_SIZE, 1, SortMode.ASC))
                .thenReturn(breedsToReturn2)
            viewModel.onChangeBreedScrollPosition(scrollPosition)

            assertEquals(breedsToReturn1 + breedsToReturn2, viewModel.breeds.value)
        }
    }

    @Test
    fun `get breeds after sort to desc`() {
        val breedsToReturn = breeds.takeLast(BreedListViewModel.PAGE_SIZE).asReversed()

        runBlocking {
            Mockito.`when`(repository.getBreeds(BreedListViewModel.PAGE_SIZE, 0, SortMode.DESC))
                .thenReturn(breedsToReturn)

            viewModel.onSortClicked()

            assertEquals(breedsToReturn, viewModel.breeds.value)
        }
    }
}