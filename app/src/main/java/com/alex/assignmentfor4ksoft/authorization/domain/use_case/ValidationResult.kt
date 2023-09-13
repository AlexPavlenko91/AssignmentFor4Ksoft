package com.alex.assignmentfor4ksoft.authorization.domain.use_case

import com.alex.assignmentfor4ksoft.utils.UiText

data class ValidationResult(
    val successful: Boolean,
    val shouldRemember: Boolean = false,
    val errorMessage: UiText? = null
)