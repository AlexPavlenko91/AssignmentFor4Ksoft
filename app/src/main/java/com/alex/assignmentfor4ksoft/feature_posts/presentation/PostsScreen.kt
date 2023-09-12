package com.alex.assignmentfor4ksoft.feature_posts.presentation

import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.alex.assignmentfor4ksoft.R
import com.alex.assignmentfor4ksoft.utils.UiEvent


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PostsScreen(
    onNavigateTo: () -> Unit,
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
                is UiEvent.NavigateTo -> onNavigateTo()
                else -> Unit
            }
        }
    }
    AndroidView(
        factory = { viewContext ->
            val view = LayoutInflater.from(viewContext).inflate(R.layout.posts_fragment, null, false)
            val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

            toolbar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.logoutMenu -> {
                        viewModel.onEvent(PostsEvent.OnLogoutClick)
                        Toast.makeText(context, "TTTTTTTTTTTT", Toast.LENGTH_SHORT).show()
                        onNavigateTo.invoke()
                    true
                    }
                    else -> false
                }
            }
            view
        },
        update = { view ->

        }
    )
}