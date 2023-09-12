package com.alex.assignmentfor4ksoft.feature_posts.presentation.posts

import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem

data class PostsState(
    val posts: List<PostItem> = emptyList(),
)