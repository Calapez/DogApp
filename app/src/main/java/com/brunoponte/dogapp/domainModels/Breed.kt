package com.brunoponte.dogapp.domainModels


data class Breed (
    val id: Long,
    val name: String?,
    val bredFor: String?,
    val breedGroup: String?,
    val lifeSpan: String?,
    val temperament: String?,
    val referenceImageId: String?,
    val origin: String?,
)