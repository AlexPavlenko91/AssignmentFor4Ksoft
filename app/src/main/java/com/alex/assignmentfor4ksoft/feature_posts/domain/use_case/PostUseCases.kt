package com.alex.assignmentfor4ksoft.feature_posts.domain.use_case

data class PostUseCases(
    val getPosts: GetPosts,
    val getPost: GetPost,
    val addPost: AddPost,
    val deletePost: DeletePost,
)