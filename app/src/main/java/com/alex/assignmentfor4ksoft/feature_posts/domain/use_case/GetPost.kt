package com.alex.assignmentfor4ksoft.feature_posts.domain.use_case

import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.feature_posts.domain.repository.PostRepository

class GetPost(
    private val repository: PostRepository
) {

    suspend operator fun invoke(id: Int): PostItem? {
        return repository.getPostById(id)
    }
}