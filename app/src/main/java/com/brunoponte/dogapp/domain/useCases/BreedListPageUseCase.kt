package com.brunoponte.dogapp.domain.useCases

import com.brunoponte.dogapp.domain.Page
import com.brunoponte.dogapp.domain.repositories.IBreedRepository
import javax.inject.Inject

class BreedListPageUseCase
@Inject
constructor(
    private val breedRepository: IBreedRepository
) {
    suspend fun execute(page: Page) = breedRepository.getBreeds(page)
}