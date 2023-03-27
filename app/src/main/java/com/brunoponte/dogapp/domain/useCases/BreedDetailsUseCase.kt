package com.brunoponte.dogapp.domain.useCases

import com.brunoponte.dogapp.domain.repository.IBreedRepository
import javax.inject.Inject

class BreedDetailsUseCase
@Inject
constructor(
    private val breedRepository: IBreedRepository
) {
    suspend fun execute(breedId: Int) = breedRepository.getBreed(breedId)
}