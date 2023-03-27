package com.brunoponte.dogapp.domain.models


data class Breed (
    val id: Int,
    val name: String?,
    val bredFor: String?,
    val breedGroup: String?,
    val lifeSpan: String?,
    val temperament: String?,
    val referenceImageId: String?,
    val origin: String?,
)