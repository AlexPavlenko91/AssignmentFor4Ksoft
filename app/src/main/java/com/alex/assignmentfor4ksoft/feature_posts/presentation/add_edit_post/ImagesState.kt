package com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post

import com.alex.assignmentfor4ksoft.feature_posts.data.data_source.remote.dto.ImageDto

data class ImagesState(
    val limit: Int = 10,
    val isLoading: Boolean = false,
    val images: List<ImageDto> = emptyList(),
)