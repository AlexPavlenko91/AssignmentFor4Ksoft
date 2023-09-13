package com.alex.assignmentfor4ksoft.feature_posts.presentation.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.assignmentfor4ksoft.core.domain.preferences.DefaultPreferences
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.PostsUseCases
import com.alex.assignmentfor4ksoft.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val preferences: DefaultPreferences,
    private val postsUseCases: PostsUseCases,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(PostsState())
    val stateFlow = _stateFlow.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getPostsJob: Job? = null

    init {
        getPosts()
    }

    fun onEvent(event: PostsEvent) {
        when (event) {
            is PostsEvent.OnLogoutClick -> {
                viewModelScope.launch {
                    preferences.saveIsRememberedUser(shouldRemember = false)
                    _uiEvent.send(UiEvent.NavigateToLogin)
                }
            }
        }
    }

    private fun getPosts() {
        getPostsJob?.cancel()
        getPostsJob = postsUseCases.getPosts()
            .onEach { posts ->
                _stateFlow.value = stateFlow.value.copy(
                    posts = posts,
                )
            }
            .launchIn(viewModelScope)
    }

}