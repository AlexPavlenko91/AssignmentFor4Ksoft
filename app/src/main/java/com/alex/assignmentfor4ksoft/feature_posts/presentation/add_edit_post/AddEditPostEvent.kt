package com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post

import androidx.compose.ui.focus.FocusState

sealed class AddEditPostEvent{
    data class EnteredComment(val value: String): AddEditPostEvent()
    data class ChangeCommentFocus(val focusState: FocusState): AddEditPostEvent()
    data class ChangeColor(val color: Int): AddEditPostEvent()
    data object SavePost: AddEditPostEvent()
}