package com.main.snapfind.features.main_photos.data.mapper

import com.main.snapfind.features.main_photos.data.local.entities.MainPhotoEntity
import com.main.snapfind.features.main_photos.data.remote.entities.PhotoItem

fun PhotoItem.mapToMainPhotoEntity(): MainPhotoEntity {
    return MainPhotoEntity(
        height = this.height,
        width = this.width,
        imageUrl = this.links.download
    )
}