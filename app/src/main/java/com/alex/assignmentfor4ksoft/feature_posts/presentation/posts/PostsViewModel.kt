package com.alex.assignmentfor4ksoft.feature_posts.presentation.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.assignmentfor4ksoft.core.domain.preferences.DefaultPreferences
import com.alex.assignmentfor4ksoft.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val preferences: DefaultPreferences
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: PostsEvent) {
        when (event) {
            is PostsEvent.OnLogoutClick -> {
                preferences.saveIsRememberedUser(shouldRemember = false)
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateToLogin)
                }
            }
        }
    }

}