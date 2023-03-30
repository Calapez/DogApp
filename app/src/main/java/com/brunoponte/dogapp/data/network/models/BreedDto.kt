package com.brunoponte.dogapp.data.network.models

import com.google.gson.annotations.SerializedName

data class BreedDto (

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("bred_for")
    val bredFor: String?,

    @SerializedName("breed_group")
    val breedGroup: String?,

    @SerializedName("life_span")
    val lifeSpan: String?,

    @SerializedName("temperament")
    val temperament: String?,

    @SerializedName("reference_image_id")
    val referenceImageId: String?,

    @SerializedName("origin")
    val origin: String?,
)
