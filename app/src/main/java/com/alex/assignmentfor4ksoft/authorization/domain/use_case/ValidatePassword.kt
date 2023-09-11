package com.alex.assignmentfor4ksoft.authorization.domain.use_case

import com.alex.assignmentfor4ksoft.R
import com.alex.assignmentfor4ksoft.utils.UiText

private const val PASSWORD_MIN_LENGTH = 6

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_password_blank)
            )
        }

        if (password.length < PASSWORD_MIN_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_password_short, PASSWORD_MIN_LENGTH)
            )
        }

        return ValidationResult(successful = true)
    }
}