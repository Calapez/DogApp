package com.brunoponte.dogapp.repository

import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.ui.breedList.SortMode
import javax.inject.Inject

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
class FakeBreedRepository @Inject constructor() : IBreedRepository {

    private val breeds = listOf(
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
    )

    override suspend fun getBreeds(pageSize: Int, page: Int, order: SortMode): List<Breed> {
        return breeds.take(3)
    }

    override suspend fun searchBreeds(query: String): List<Breed> {
        return if(query == "") listOf() else breeds.take(3)
    }

    override suspend fun getBreed(id: Int): Breed {
        return breeds[0]
    }
}