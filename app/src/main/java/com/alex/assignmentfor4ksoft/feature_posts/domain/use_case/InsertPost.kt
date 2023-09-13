package com.alex.assignmentfor4ksoft.feature_posts.domain.use_case

import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.InvalidPostException
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.feature_posts.domain.repository.PostRepository

class AddPost(
    private val repository: PostRepository
) {

    @Throws(InvalidPostException::class)
    suspend operator fun invoke(post: PostItem) {
        if(post.comment.isBlank()) {
            throw InvalidPostException("The comment of the post can't be empty.")
        }
        repository.insertPost(post)
    }
}