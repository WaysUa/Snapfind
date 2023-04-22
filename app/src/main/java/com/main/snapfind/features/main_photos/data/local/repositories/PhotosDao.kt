package com.main.snapfind.features.main_photos.data.local.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.main.snapfind.features.main_photos.data.local.entities.PhotoEntity

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhoto(photoEntity: PhotoEntity)

}