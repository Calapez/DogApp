package com.brunoponte.dogapp.ui.breedSearchList

import com.brunoponte.dogapp.repository.IBreedRepository
import javax.inject.Inject

class BreedSearchListUseCase
@Inject
constructor(
    private val breedRepository: IBreedRepository
) {
    suspend fun execute(query: String) = breedRepository.searchBreeds(query)
}