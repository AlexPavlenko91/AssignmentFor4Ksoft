package com.alex.assignmentfor4ksoft.authorization.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.assignmentfor4ksoft.core.domain.preferences.DefaultPreferences
import com.alex.assignmentfor4ksoft.authorization.domain.use_case.AuthorizationUseCases
import com.alex.assignmentfor4ksoft.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authorizationUseCases: AuthorizationUseCases,
    private val preferences: DefaultPreferences,
) : ViewModel() {

    var state by mutableStateOf(AuthorizationState())

    private val validationEventChannel = Channel<UiEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: AuthorizationEvent) {
        when (event) {
            is AuthorizationEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is AuthorizationEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is AuthorizationEvent.RememberUser -> {
                state = state.copy(shouldRemember = event.shouldRemember)
            }

            is AuthorizationEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = authorizationUseCases.validateEmail.execute(state.email)
        val passwordResult = authorizationUseCases.validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any { !it.successful }

        if (hasError){
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        }

        viewModelScope.launch {
            if (state.shouldRemember){
                preferences.saveIsRememberedUser(state.shouldRemember)
            }
            validationEventChannel.send(UiEvent.Success)
        }
    }
/*
    sealed class ValidationEvent{
        data object Success: ValidationEvent()
    }*/
}