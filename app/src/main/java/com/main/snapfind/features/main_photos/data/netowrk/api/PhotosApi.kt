package com.main.snapfind.features.main_photos.data.netowrk.api

import com.main.snapfind.features.main_photos.data.netowrk.entities.PhotoItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosApi {

    @GET("photos")
    fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<PhotoItem>>

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
    }
}