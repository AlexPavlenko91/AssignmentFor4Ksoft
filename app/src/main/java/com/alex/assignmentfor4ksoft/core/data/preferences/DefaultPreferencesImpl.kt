package com.alex.assignmentfor4ksoft.core.data.preferences

import android.content.SharedPreferences
import com.alex.assignmentfor4ksoft.core.domain.preferences.DefaultPreferences

class DefaultPreferencesImpl(
    private val sharedPref: SharedPreferences
): DefaultPreferences {

    override fun saveIsRememberedUser(shouldRemember: Boolean) {
        sharedPref.edit()
            .putBoolean(DefaultPreferences.KEY_SHOULD_REMEMBER, shouldRemember)
            .apply()
    }

    override fun loadIsRememberedUser(): Boolean {
        return sharedPref.getBoolean(
            DefaultPreferences.KEY_SHOULD_REMEMBER,
            false
        )
    }
}