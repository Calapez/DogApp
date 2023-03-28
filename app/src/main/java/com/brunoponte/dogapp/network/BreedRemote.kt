package com.brunoponte.dogapp.network

import com.brunoponte.dogapp.data.IBreedRemote
import com.brunoponte.dogapp.domain.models.Breed
import com.brunoponte.dogapp.network.models.BreedDtoMapper
import com.brunoponte.dogapp.presentation.breedList.SortMode
import javax.inject.Inject

class BreedRemote
@Inject
constructor(
    private val requestService: IRequestService
) : IBreedRemote {

    override suspend fun getBreeds(pageSize: Int, page: Int, order: SortMode): List<Breed> {
        return BreedDtoMapper.toDomainModelList(
            requestService.getBreeds(pageSize, page, getSortModeString(order)))
    }

    override suspend fun searchBreeds(query: String): List<Breed> {
        return BreedDtoMapper.toDomainModelList(
            requestService.searchBreeds(query))
    }

    private fun getSortModeString(sortMode: SortMode) = when (sortMode) {
        SortMode.ASC -> "Asc"
        SortMode.DESC -> "Desc"
    }
}