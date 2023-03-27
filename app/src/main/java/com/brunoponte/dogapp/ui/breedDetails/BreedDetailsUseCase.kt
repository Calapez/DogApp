package com.brunoponte.dogapp.ui.breedDetails

import com.brunoponte.dogapp.repository.IBreedRepository
import javax.inject.Inject

class BreedDetailsUseCase
@Inject
constructor(
    private val breedRepository: IBreedRepository
) {
    suspend fun execute(breedId: Int) = breedRepository.getBreed(breedId)
}