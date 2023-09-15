package com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.assignmentfor4ksoft.R
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.InvalidPostException
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.AddEditPostUseCases
import com.alex.assignmentfor4ksoft.utils.UiEvent
import com.alex.assignmentfor4ksoft.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditPostViewModel @Inject constructor(
    private val addEditPostUseCases: AddEditPostUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _postComment = mutableStateOf(
        PostTextFieldState()
    )
    val postComment: State<PostTextFieldState> = _postComment

    private val _postColor = mutableIntStateOf(PostItem.postColors.random().toArgb())
    val postColor: State<Int> = _postColor

    private val _imageUrl = mutableStateOf("")
    val imageUrl: MutableState<String> = _imageUrl

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentPostId: Int? = null

    var stateImages by mutableStateOf(ImagesState())
        private set

    init {
        getImages()

        savedStateHandle.get<Int>("postId")?.let { postId ->
            if (postId != -1) {
                viewModelScope.launch {
                    addEditPostUseCases.getPost(postId)?.also { post ->
                        currentPostId = post.id
                        _postComment.value = _postComment.value.copy(
                            text = UiText.DynamicString(post.comment).value,
                            isHintVisible = false
                        )
                        _postColor.intValue = post.color
                        _imageUrl.value = post.imageUrl
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditPostEvent) {
        when (event) {
            is AddEditPostEvent.EnteredComment -> {
                _postComment.value = _postComment.value.copy(
                    text = UiText.DynamicString(event.value).value
                )
            }

            is AddEditPostEvent.ChangeCommentFocus -> {
                _postComment.value = _postComment.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _postComment.value.text.isBlank()
                )
            }

            is AddEditPostEvent.ChangeColor -> {
                _postColor.intValue = event.color
            }

            is AddEditPostEvent.SelectImage -> {
                _imageUrl.value = event.value
            }

            is AddEditPostEvent.SavePost -> {
                viewModelScope.launch {
                    try {
                        addEditPostUseCases.addPost(
                            PostItem(
                                comment = postComment.value.text,
                                dateTime = System.currentTimeMillis(),
                                color = postColor.value,
                                imageUrl = imageUrl.value,
                                id = currentPostId
                            )
                        )
                        _eventFlow.emit(UiEvent.SavePost)
                    } catch (e: InvalidPostException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = UiText.StringResource(R.string.error_saving_post)
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getImages() {
        viewModelScope.launch {
            addEditPostUseCases
                .getImages(stateImages.limit)
                .onSuccess { loadedImages ->
                    stateImages = stateImages.copy(
                        images = loadedImages,
                        isLoading = false,
                    )
                }
                .onFailure {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(R.string.error_something_went_wrong)
                        )
                    )
                }
        }
    }

}