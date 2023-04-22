package com.main.snapfind.features.main_photos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotoEntity(
    @PrimaryKey
    val id: Int,
    val height: Int,
    val width: Int
)