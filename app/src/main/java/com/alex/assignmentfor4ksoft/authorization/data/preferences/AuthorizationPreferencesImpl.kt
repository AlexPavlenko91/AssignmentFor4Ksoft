package com.alex.assignmentfor4ksoft.authorization.data.preferences

import android.content.SharedPreferences

class AuthorizationPreferencesImpl(
    private val sharedPref: SharedPreferences
): AuthorizationPreferences {
    override fun saveEmail(email: String) {
        sharedPref.edit()
            .putString(AuthorizationPreferences.KEY_EMAIL, email)
            .apply()
    }

    override fun savePassword(password: String) {
        sharedPref.edit()
            .putString(AuthorizationPreferences.KEY_PASSWORD, password)
            .apply()
    }

    override fun saveShouldRememberUser(shouldRemember: Boolean) {
        sharedPref.edit()
            .putBoolean(AuthorizationPreferences.KEY_SHOULD_REMEMBER, shouldRemember)
            .apply()
    }

    override fun loadShouldRememberUser(): Boolean {
        return sharedPref.getBoolean(
            AuthorizationPreferences.KEY_SHOULD_REMEMBER,
            false
        )
    }
}