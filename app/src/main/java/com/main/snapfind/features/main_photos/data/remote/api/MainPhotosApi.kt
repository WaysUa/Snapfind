package com.main.snapfind.features.main_photos.data.remote.api

import com.main.snapfind.BuildConfig
import com.main.snapfind.features.main_photos.data.remote.entities.PhotoItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MainPhotosApi {

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int,
        @Header("Authorization") authorizationKey: String = BuildConfig.AUTH_KEY
    ): Response<List<PhotoItem>>

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
    }
}