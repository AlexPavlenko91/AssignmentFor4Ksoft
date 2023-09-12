package com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.assignmentfor4ksoft.R
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.InvalidPostException
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.PostUseCases
import com.alex.assignmentfor4ksoft.utils.UiEvent
import com.alex.assignmentfor4ksoft.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditPostViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _postComment = mutableStateOf(
        PostTextFieldState()
    )
    val postComment: State<PostTextFieldState> = _postComment

    private val _noteColor = mutableIntStateOf(PostItem.postColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    postUseCases.getPost(noteId)?.also { post ->
                        currentNoteId = post.id
                        _postComment.value = _postComment.value.copy(
                            text = UiText.DynamicString(post.comment).value,
                            isHintVisible = false
                        )
                        _noteColor.intValue = post.color
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
                _noteColor.intValue = event.color
            }

            is AddEditPostEvent.SavePost -> {
                viewModelScope.launch {
                    try {
                        postUseCases.addPost(
                            PostItem(
                                comment = postComment.value.text,
                                dateTime = System.currentTimeMillis(),
                                color = noteColor.value,
                                imageUrl = "https://picsum.photos/id/3/5000/3333",
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SavePost)
                    } catch (e: InvalidPostException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message?.let { UiText.DynamicString(it) }
                                    ?: UiText.StringResource(R.string.error_saving_post)
                            )
                        )
                    }
                }
            }
        }
    }
}