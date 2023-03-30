package com.brunoponte.dogapp.domain.useCases

import com.brunoponte.dogapp.domain.repositories.IBreedRepository
import javax.inject.Inject

class BreedSearchListUseCase
@Inject
constructor(
    private val breedRepository: IBreedRepository
) {
    suspend fun execute(query: String) = breedRepository.searchBreeds(query)
}