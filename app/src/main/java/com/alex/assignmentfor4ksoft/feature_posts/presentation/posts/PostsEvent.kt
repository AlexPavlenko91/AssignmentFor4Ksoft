package com.alex.assignmentfor4ksoft.feature_posts.presentation.posts

sealed class PostsEvent {
    data object OnLogoutClick : PostsEvent()
}