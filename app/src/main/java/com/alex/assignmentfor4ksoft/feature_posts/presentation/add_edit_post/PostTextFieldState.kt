package com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post

data class PostTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)