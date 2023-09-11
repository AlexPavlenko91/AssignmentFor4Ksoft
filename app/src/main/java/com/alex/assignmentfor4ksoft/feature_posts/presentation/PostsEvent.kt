package com.alex.assignmentfor4ksoft.feature_posts.presentation

sealed class PostsEvent {
    data object OnLogoutClick : PostsEvent()
}