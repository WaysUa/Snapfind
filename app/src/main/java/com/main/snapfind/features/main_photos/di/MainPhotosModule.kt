package com.main.snapfind.features.main_photos.di

import android.content.Context
import dagger.Module
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.main.snapfind.app.database.SnapfindDatabase
import com.main.snapfind.features.main_photos.data.local.entities.MainPhotoEntity
import com.main.snapfind.features.main_photos.data.remote.api.MainPhotosApi
import com.main.snapfind.features.main_photos.data.remote.paging.PhotosRemoteMediator
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object MainPhotosModule {

    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): SnapfindDatabase {
        return Room.databaseBuilder(
            context,
            SnapfindDatabase::class.java,
            "snapfind.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBeerApi(): MainPhotosApi {
        return Retrofit.Builder()
            .baseUrl(MainPhotosApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(MainPhotosApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBeerPager(
        snapfindDatabase: SnapfindDatabase,
        mainPhotosApi: MainPhotosApi
    ): Pager<Int, MainPhotoEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PhotosRemoteMediator(
                snapfindDatabase = snapfindDatabase,
                mainPhotosApi = mainPhotosApi
            ),
            pagingSourceFactory = {
                snapfindDatabase.photosDao().pagingSource()
            }
        )
    }
}