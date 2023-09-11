package com.alex.assignmentfor4ksoft.authorization.domain.use_case

data class AuthorizationUseCases (
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword
)