package com.alex.assignmentfor4ksoft.feature_posts.domain.use_case

import com.alex.assignmentfor4ksoft.feature_posts.data.data_source.remote.dto.ImageDto
import com.alex.assignmentfor4ksoft.feature_posts.domain.repository.PostRepository

class GetImages(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        limit: Int
    ): Result<List<ImageDto>> {
        return repository.getImages(limit)
    }
}