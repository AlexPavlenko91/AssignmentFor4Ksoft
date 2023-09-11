package com.alex.assignmentfor4ksoft.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.alex.assignmentfor4ksoft.core.domain.preferences.DefaultPreferences
import com.alex.assignmentfor4ksoft.core.data.preferences.DefaultPreferencesImpl
import com.alex.assignmentfor4ksoft.authorization.domain.use_case.AuthorizationUseCases
import com.alex.assignmentfor4ksoft.authorization.domain.use_case.ValidateEmail
import com.alex.assignmentfor4ksoft.authorization.domain.use_case.ValidatePassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): DefaultPreferences {
        return DefaultPreferencesImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAuthorizationUseCases(): AuthorizationUseCases {
        return AuthorizationUseCases(
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword()
        )
    }
}