package com.alex.assignmentfor4ksoft.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.alex.assignmentfor4ksoft.authorization.domain.use_case.AuthorizationUseCases
import com.alex.assignmentfor4ksoft.authorization.domain.use_case.ValidateEmail
import com.alex.assignmentfor4ksoft.authorization.domain.use_case.ValidatePassword
import com.alex.assignmentfor4ksoft.core.data.preferences.DefaultPreferencesImpl
import com.alex.assignmentfor4ksoft.core.domain.preferences.DefaultPreferences
import com.alex.assignmentfor4ksoft.feature_posts.data.data_source.local.PostDatabase
import com.alex.assignmentfor4ksoft.feature_posts.data.data_source.remote.ImagesApi
import com.alex.assignmentfor4ksoft.feature_posts.data.repository.PostRepositoryImpl
import com.alex.assignmentfor4ksoft.feature_posts.domain.repository.PostRepository
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.AddEditPostUseCases
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.AddPost
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.DeletePost
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.GetImages
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.GetPost
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.GetPosts
import com.alex.assignmentfor4ksoft.feature_posts.domain.use_case.PostsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
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
    fun providePostRepository(db: PostDatabase, api: ImagesApi): PostRepository {
        return PostRepositoryImpl(
            db.postDao,
            api
        )
    }

    @Provides
    @Singleton
    fun providePostsUseCases(repository: PostRepository): PostsUseCases {
        return PostsUseCases(
            getPosts = GetPosts(repository),
        )
    }

    @Provides
    @Singleton
    fun provideAddEditPostUseCases(repository: PostRepository): AddEditPostUseCases {
        return AddEditPostUseCases(
            getPost = GetPost(repository),
            addPost = AddPost(repository),
            deletePost = DeletePost(repository),
            getImages = GetImages(repository),
        )
    }

    @Provides
    @Singleton
    fun provideImagesApi(): ImagesApi {
        return Retrofit.Builder()
            .baseUrl(ImagesApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
}