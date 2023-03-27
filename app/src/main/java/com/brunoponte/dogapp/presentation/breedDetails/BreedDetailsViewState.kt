package com.brunoponte.dogapp.presentation.breedDetails

data class BreedDetailsViewState(
    val id: Int,
    val name: String?,
    val breedGroup: String?,
    val referenceImageId: String?,
    val origin: String?,
    val temperament: String?,
)
