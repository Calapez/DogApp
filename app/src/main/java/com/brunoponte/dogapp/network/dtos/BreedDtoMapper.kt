package com.brunoponte.dogapp.network.dtos

import com.brunoponte.dogapp.domainModels.Breed

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