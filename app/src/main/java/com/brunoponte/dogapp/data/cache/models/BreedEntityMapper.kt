package com.brunoponte.dogapp.data.cache.models

import com.brunoponte.dogapp.domain.models.Breed

class BreedEntityMapper {

    companion object {

        fun mapToDomainModel(entity: BreedEntity) = Breed (
            id = entity.id,
            name = entity.name,
            bredFor = entity.bredFor,
            breedGroup = entity.breedGroup,
            lifeSpan = entity.lifeSpan,
            temperament = entity.temperament,
            referenceImageId = entity.referenceImageId,
            origin = entity.origin
        )

        fun mapToEntity(model: Breed) = BreedEntity (
            id = model.id,
            name = model.name,
            bredFor = model.bredFor,
            breedGroup = model.breedGroup,
            lifeSpan = model.lifeSpan,
            temperament = model.temperament,
            referenceImageId = model.referenceImageId,
            origin = model.origin
        )

        fun toEntityList(domainModelList: List<Breed>) = domainModelList.map { domainModel ->
            mapToEntity(domainModel)
        }

        fun toDomainModelList(entityList: List<BreedEntity>) = entityList.map { entity ->
            mapToDomainModel(entity)
        }

    }

}