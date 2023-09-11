package com.alex.assignmentfor4ksoft.authorization.domain.use_case

import com.alex.assignmentfor4ksoft.R
import com.alex.assignmentfor4ksoft.utils.UiText
import com.alex.assignmentfor4ksoft.utils.validations.EmailValidator


private const val EMAIL_MIN_LENGTH = 6

class ValidateEmail {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_email_blank)
            )
        }

        if (email.length < EMAIL_MIN_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_email_short, EMAIL_MIN_LENGTH)
            )
        }

        if (!EmailValidator.isValid(email)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_email_invalid)
            )
        }
        return ValidationResult(successful = true)
    }
}