package com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.alex.assignmentfor4ksoft.R
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.AddEditPostEvent
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.AddEditPostViewModel
import com.alex.assignmentfor4ksoft.utils.UiText

@Composable
fun OnBackPressedDialog(
    shouldShowDialog: MutableState<Boolean>,
    viewModel: AddEditPostViewModel,
    navController: NavController
) {
    AlertDialog(
        onDismissRequest = { shouldShowDialog.value = false },
        title = { Text(UiText.StringResource(R.string.save_title).asString()) },
        text = { Text(UiText.StringResource(R.string.save_text).asString()) },
        confirmButton = {
            TextButton(onClick = {
                shouldShowDialog.value = false
                viewModel.onEvent(AddEditPostEvent.SavePost)
            }) {
                Text(UiText.StringResource(R.string.save).asString().uppercase())
            }
        },
        dismissButton = {
            TextButton(onClick = {
                shouldShowDialog.value = false
                navController.navigateUp()
            }) {
                Text(UiText.StringResource(R.string.exit_without_saving).asString().uppercase())
            }
        },
    )
}