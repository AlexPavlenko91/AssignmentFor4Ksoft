package com.alex.assignmentfor4ksoft.authorization.presentation

import com.alex.assignmentfor4ksoft.utils.UiText


data class AuthorizationState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val shouldRemember: Boolean = false
)