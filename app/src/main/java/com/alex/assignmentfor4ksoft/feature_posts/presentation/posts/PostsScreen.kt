package com.alex.assignmentfor4ksoft.feature_posts.presentation.posts

import android.view.LayoutInflater
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alex.assignmentfor4ksoft.R
import com.alex.assignmentfor4ksoft.databinding.PostsFragmentBinding
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.utils.Screen
import com.alex.assignmentfor4ksoft.utils.UiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PostsScreen(
    navController: NavController,
    viewModel: PostsViewModel = hiltViewModel(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                    keyboardController?.hide()
                }

                is UiEvent.NavigateToLogin -> navController.navigate(Screen.AuthorizationScreen.route)
                else -> Unit
            }
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val binding = PostsFragmentBinding.inflate(LayoutInflater.from(context))
    AndroidView(
        factory = {
            binding.toolbarPosts.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.logoutMenu -> {
                        viewModel.onEvent(PostsEvent.OnLogoutClick)
                        true
                    }

                    else -> false
                }
            }
            coroutineScope.launch {
                viewModel.stateFlow.collectLatest { value ->
                    val postsAdapter = PostsAdapter(
                        value.posts
                    ) { item -> navigateToEditPost(navController, item) }
                    binding.rvPosts.adapter = postsAdapter
                }
            }

            binding.fabPosts.setOnClickListener {
                navController.navigate(Screen.AddEditPostScreen.route)
            }
            binding.root
        }
    )
}

private fun navigateToEditPost(navController: NavController, item: PostItem) {
    navController.navigate(
        Screen.AddEditPostScreen.route + "?postId=${item.id}&postColor=${item.color}"
    )
}
