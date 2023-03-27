package com.brunoponte.dogapp.network.models

import com.brunoponte.dogapp.domain.models.Breed

class BreedDtoMapper {

    companion object {

        fun mapToDomainModel(dto: BreedDto) = Breed (
            id = dto.id,
            name = dto.name,
            bredFor = dto.bredFor,
            breedGroup = dto.breedGroup,
            lifeSpan = dto.lifeSpan,
            temperament = dto.temperament,
            referenceImageId = dto.referenceImageId,
            origin = dto.origin
        )

        fun toDomainModelList(dtoList: List<BreedDto>) = dtoList.map { dto ->
            mapToDomainModel(dto)
        }

    }

}