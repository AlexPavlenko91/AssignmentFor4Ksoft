package com.alex.assignmentfor4ksoft.feature_posts.data.data_source.remote.dto

import com.squareup.moshi.Json

data class ImageDto(
    val author: String,
    @field:Json(name = "download_url")
    val downloadUrl: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)