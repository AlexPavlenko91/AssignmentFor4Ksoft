package com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import com.alex.assignmentfor4ksoft.R
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.AddEditPostEvent
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.AddEditPostViewModel
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.PostTextFieldState
import com.alex.assignmentfor4ksoft.utils.UiText

@Composable
fun TransparentHintTextField(
    innerPadding: PaddingValues,
    commentState: PostTextFieldState,
    viewModel: AddEditPostViewModel,
    isStateChanged: MutableState<Boolean>
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(innerPadding)
    ) {
        BasicTextField(
            value = commentState.text,
            onValueChange = {
                viewModel.onEvent(AddEditPostEvent.EnteredComment(it))
                isStateChanged.value = true
            },
            singleLine = false,
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    viewModel.onEvent(AddEditPostEvent.ChangeCommentFocus(it))
                }
        )
        if (commentState.isHintVisible) {
            Text(
                text = UiText.StringResource(R.string.post_comment_hint).asString(),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray
            )
        }
    }
}