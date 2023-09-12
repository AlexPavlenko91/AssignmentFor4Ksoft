package com.alex.assignmentfor4ksoft.feature_posts.domain.use_case

import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.feature_posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPosts(
    private val repository: PostRepository
) {

    operator fun invoke(
    ): Flow<List<PostItem>> {
        return repository.getPosts()
    }
}