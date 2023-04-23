package com.main.snapfind.features.main_photos.data.local.repositories

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.main.snapfind.features.main_photos.data.local.entities.MainPhotoEntity

@Dao
interface MainPhotosDao {

    @Upsert
    suspend fun upsertPhotos(photos: List<MainPhotoEntity>)

    @Query("SELECT * FROM mainphotoentity")
    fun pagingSource(): PagingSource<Int, MainPhotoEntity>

    @Query("DELETE FROM mainphotoentity")
    suspend fun clearAll()

}