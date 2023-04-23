package com.main.snapfind.features.main_photos.data.remote.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.main.snapfind.app.database.SnapfindDatabase
import com.main.snapfind.features.main_photos.data.local.entities.MainPhotoEntity
import com.main.snapfind.features.main_photos.data.mapper.mapToMainPhotoEntity
import com.main.snapfind.features.main_photos.data.remote.api.MainPhotosApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PhotosRemoteMediator(
    private val snapfindDatabase: SnapfindDatabase,
    private val mainPhotosApi: MainPhotosApi
): RemoteMediator<Int, MainPhotoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MainPhotoEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val photos = mainPhotosApi.getPhotos(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            Log.d("MyLog", "photos: $photos")

            snapfindDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    snapfindDatabase.photosDao().clearAll()
                }
                val photoEntities = photos.body()?.map { it.mapToMainPhotoEntity() }
                if (photoEntities != null) {
                    snapfindDatabase.photosDao().upsertPhotos(photoEntities)
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = photos.body()?.isEmpty() == true
            )
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}