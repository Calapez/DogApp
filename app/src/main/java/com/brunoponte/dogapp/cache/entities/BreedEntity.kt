package com.brunoponte.dogapp.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class BreedEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long?,

)