package com.alex.assignmentfor4ksoft.utils

sealed class Screen(val route: String) {
    data object AuthorizationScreen: Screen("authorization_screen")
    data object PostsScreen: Screen("posts_screen")
    data object AddEditPostScreen: Screen("add_edit_post_screen")
}
