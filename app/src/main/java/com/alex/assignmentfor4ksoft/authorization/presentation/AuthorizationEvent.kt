package com.alex.assignmentfor4ksoft.authorization.presentation

sealed class AuthorizationEvent {
    data class EmailChanged(val email: String) : AuthorizationEvent()
    data class PasswordChanged(val password: String) : AuthorizationEvent()
    data class RememberUser(val shouldRemember: Boolean) : AuthorizationEvent()

    data object Submit : AuthorizationEvent()
}