package com.alex.assignmentfor4ksoft.core.domain.preferences

interface DefaultPreferences {
    fun saveIsRememberedUser(shouldRemember: Boolean)
    fun loadIsRememberedUser(): Boolean

    companion object {
        const val KEY_SHOULD_REMEMBER = "should_remember"
    }
}