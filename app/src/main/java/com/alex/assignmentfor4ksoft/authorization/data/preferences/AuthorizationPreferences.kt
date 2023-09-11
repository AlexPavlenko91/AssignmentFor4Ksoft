package com.alex.assignmentfor4ksoft.authorization.data.preferences

interface AuthorizationPreferences {
    fun saveEmail(email: String)
    fun savePassword(password: String)
    fun saveShouldRememberUser(shouldRemember: Boolean)
    fun loadShouldRememberUser(): Boolean

    companion object {
        const val KEY_EMAIL = "email"
        const val KEY_PASSWORD = "password"
        const val KEY_SHOULD_REMEMBER = "should_remember"
    }
}