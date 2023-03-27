package com.brunoponte.dogapp.domain.useCases

import com.brunoponte.dogapp.presentation.breedList.SortMode
import com.brunoponte.dogapp.domain.repository.IBreedRepository
import javax.inject.Inject

class BreedListPageUseCase
@Inject
constructor(
    private val breedRepository: IBreedRepository
) {
    suspend fun execute(pageSize: Int, page: Int, order: SortMode) =
        breedRepository.getBreeds(pageSize, page, order)
}