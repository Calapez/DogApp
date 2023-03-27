package com.brunoponte.dogapp.ui.breedList

import com.brunoponte.dogapp.repository.IBreedRepository
import javax.inject.Inject

class BreedListPageUseCase
@Inject
constructor(
    private val breedRepository: IBreedRepository
) {
    suspend fun execute(pageSize: Int, page: Int, order: SortMode) =
        breedRepository.getBreeds(pageSize, page, order)
}