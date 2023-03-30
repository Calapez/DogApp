package com.brunoponte.dogapp.data.cache.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class BreedEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "bred_for")
    var bredFor: String?,

    @ColumnInfo(name = "breed_group")
    var breedGroup: String?,

    @ColumnInfo(name = "life_span")
    var lifeSpan: String?,

    @ColumnInfo(name = "temperament")
    var temperament: String?,

    @ColumnInfo(name = "reference_image_id")
    var referenceImageId: String?,

    @ColumnInfo(name = "origin")
    var origin: String?,

)