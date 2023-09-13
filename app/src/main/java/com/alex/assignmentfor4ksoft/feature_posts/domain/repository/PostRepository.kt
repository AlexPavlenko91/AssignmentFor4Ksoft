package com.alex.assignmentfor4ksoft.feature_posts.domain.repository

import com.alex.assignmentfor4ksoft.feature_posts.data.data_source.remote.dto.ImageDto
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getPosts(): Flow<List<PostItem>>

    suspend fun getPostById(id: Int): PostItem?
    suspend fun insertPost(post: PostItem)

    suspend fun deletePost(post: PostItem)

    suspend fun getImages(limit: Int): Result<List<ImageDto>>

}