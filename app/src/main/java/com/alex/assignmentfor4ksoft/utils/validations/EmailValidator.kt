package com.alex.assignmentfor4ksoft.utils.validations

import androidx.core.util.PatternsCompat

object EmailValidator{

    fun isValid(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }
}