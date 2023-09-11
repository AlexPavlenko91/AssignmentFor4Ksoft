package com.alex.assignmentfor4ksoft.utils

sealed class UiEvent {
    data object Success: UiEvent()
    data object NavigateTo: UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
}