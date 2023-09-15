package com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alex.assignmentfor4ksoft.R
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.components.BoxImages
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.components.OnBackPressedDialog
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.components.RowColors
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.components.TransparentHintTextField
import com.alex.assignmentfor4ksoft.utils.UiEvent
import com.alex.assignmentfor4ksoft.utils.UiText
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPostScreen(
    navController: NavController,
    postColor: Int,
    viewModel: AddEditPostViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val commentState = viewModel.postComment.value
    val snackbarHostState = remember { SnackbarHostState() }
    val shouldShowDialog = remember { mutableStateOf(false) }
    val isStateChanged = remember { mutableStateOf(false) }
    val backgroundAnimatable = remember {
        Animatable(Color(if (postColor != -1) postColor else viewModel.postColor.value))
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(message = event.message.toString())
                is UiEvent.SavePost -> navController.navigateUp()
                else -> Unit
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(AddEditPostEvent.SavePost) },
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = UiText.StringResource(R.string.save).asString(context)
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundAnimatable.value)
                .padding(innerPadding)
        ) {
            if (viewModel.stateImages.images.isEmpty()) {
                Text(text = UiText.StringResource(R.string.images_blank).asString(context))
            } else {
                BoxImages(viewModel, isStateChanged, context)
            }

            Spacer(modifier = Modifier.height(16.dp))
            RowColors(viewModel, scope, backgroundAnimatable, isStateChanged)

            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(innerPadding, commentState, viewModel, isStateChanged)
        }
    }

    if (isStateChanged.value) {
        BackHandler(enabled = true, onBack = { shouldShowDialog.value = true })
    }

    if (shouldShowDialog.value) {
        OnBackPressedDialog(shouldShowDialog, viewModel, navController)
    }
}