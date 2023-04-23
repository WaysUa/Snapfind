package com.main.snapfind.features.main_photos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MainPhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val height: Int,
    val width: Int,
    val imageUrl: String
)