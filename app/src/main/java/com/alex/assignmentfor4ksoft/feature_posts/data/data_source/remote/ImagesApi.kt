package com.alex.assignmentfor4ksoft.feature_posts.data.data_source.remote

import com.alex.assignmentfor4ksoft.feature_posts.data.data_source.remote.dto.ImageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {

    @GET("list")
    suspend fun getImages(
        @Query("limit") limit: Int,
    ): List<ImageDto>

    companion object {
        const val BASE_URL = "https://picsum.photos/v2/"
    }
}