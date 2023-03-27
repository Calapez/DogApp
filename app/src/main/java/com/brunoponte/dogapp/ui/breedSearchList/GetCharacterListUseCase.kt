package com.brunoponte.dogapp.ui.breedSearchList

import com.brunoponte.dogapp.repository.BreedRepository
import javax.inject.Inject

class BreedSearchListUseCase
@Inject
constructor(
    private val breedRepository: BreedRepository
) {
    suspend fun execute(query: String) = breedRepository.searchBreeds(query)
}