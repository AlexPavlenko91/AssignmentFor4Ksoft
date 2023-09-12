package com.alex.assignmentfor4ksoft.feature_posts.domain.use_case

import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.feature_posts.domain.repository.PostRepository

class DeletePost(
    private val repository: PostRepository
) {

    suspend operator fun invoke(post: PostItem) {
        repository.deletePost(post)
    }
}