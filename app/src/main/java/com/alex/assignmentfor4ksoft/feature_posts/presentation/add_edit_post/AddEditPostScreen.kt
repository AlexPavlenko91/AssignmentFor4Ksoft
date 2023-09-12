package com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alex.assignmentfor4ksoft.R
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.components.TransparentHintTextField
import com.alex.assignmentfor4ksoft.utils.UiEvent
import com.alex.assignmentfor4ksoft.utils.UiText
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditPostScreen(
    navController: NavController,
    postColor: Int,
    viewModel: AddEditPostViewModel = hiltViewModel()
) {
    val contentState = viewModel.postComment.value

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (postColor != -1) postColor else viewModel.noteColor.value)
        )
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.toString()
                    )
                }

                is UiEvent.SavePost -> {
                    navController.navigateUp()
                }
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(noteBackgroundAnimatable.value)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PostItem.postColors.forEach { color ->
                val colorInt = color.toArgb()
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .shadow(15.dp, CircleShape)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = 3.dp,
                            color = if (viewModel.noteColor.value == colorInt) {
                                Color.Black
                            } else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            scope.launch {
                                noteBackgroundAnimatable.animateTo(
                                    targetValue = Color(colorInt),
                                    animationSpec = tween(
                                        durationMillis = 500
                                    )
                                )
                            }
                            viewModel.onEvent(AddEditPostEvent.ChangeColor(colorInt))
                        }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TransparentHintTextField(
            text = contentState.text,
            hint = UiText.StringResource(R.string.post_comment_hint).asString(),
            onValueChange = {
                viewModel.onEvent(AddEditPostEvent.EnteredComment(it))
            },
            onFocusChange = {
                viewModel.onEvent(AddEditPostEvent.ChangeCommentFocus(it))
            },
            isHintVisible = contentState.isHintVisible,
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxHeight()
        )
    }
}