package com.alex.assignmentfor4ksoft.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.alex.assignmentfor4ksoft.core.domain.preferences.DefaultPreferences
import com.alex.assignmentfor4ksoft.core.data.preferences.DefaultPreferencesImpl
import com.alex.assignmentfor4ksoft.authorization.domain.use_case.AuthorizationUseCases
import com.alex.assignmentfor4ksoft.authorization.domain.use_case.ValidateEmail
import com.alex.assignmentfor4ksoft.authorization.domain.use_case.ValidatePassword
import com.alex.assignmentfor4ksoft.feature_posts.data.data_source.PostDatabase
import com.alex.assignmentfor4ksoft.feature_posts.data.repository.PostRepositoryImpl
import com.alex.assignmentfor4ksoft.feature_posts.domain.repository.PostRepository
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.AddPost
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.DeletePost
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.GetPost
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.GetPosts
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.PostUseCases
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

    @Provides
    @Singleton
    fun providePostDatabase(app: Application): PostDatabase {
        return Room.databaseBuilder(
            app,
            PostDatabase::class.java,
            PostDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePostRepository(db: PostDatabase): PostRepository {
        return PostRepositoryImpl(db.postDao)
    }

    @Provides
    @Singleton
    fun providePostUseCases(repository: PostRepository): PostUseCases {
        return PostUseCases(
            getPosts = GetPosts(repository),
            getPost = GetPost(repository),
            addPost = AddPost(repository),
            deletePost = DeletePost(repository),
        )
    }
}