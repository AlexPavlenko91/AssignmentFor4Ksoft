package com.alex.assignmentfor4ksoft.feature_posts.presentation.posts

import android.view.LayoutInflater
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.recyclerview.widget.RecyclerView
import com.alex.assignmentfor4ksoft.R
import com.alex.assignmentfor4ksoft.databinding.PostsFragmentBinding
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.utils.UiEvent


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PostsScreen(
    navigateToLogin: () -> Unit,
    navigateToAddEditScreen: () -> Unit,
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

                is UiEvent.NavigateToLogin -> navigateToLogin()
                else -> Unit
            }
        }
    }
    AndroidView(
        factory = { viewContext ->
            val binding = PostsFragmentBinding.inflate(LayoutInflater.from(viewContext))

            binding.toolbarPosts.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.logoutMenu -> {
                        viewModel.onEvent(PostsEvent.OnLogoutClick)
                        true
                    }

                    else -> false
                }
            }

                initPostsAdapter(binding.rvPosts, emptyList())
            binding.fabPosts.setOnClickListener {
                navigateToAddEditScreen.invoke()
            }
            binding.root
        }
    )
}

fun initPostsAdapter(rv: RecyclerView, posts: List<PostItem>) {
    val postsAdapter = PostsAdapter(posts)
    rv.adapter = postsAdapter
}
