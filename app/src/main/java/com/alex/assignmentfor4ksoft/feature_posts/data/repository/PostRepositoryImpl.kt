package com.alex.assignmentfor4ksoft.feature_posts.data.repository

import com.alex.assignmentfor4ksoft.feature_posts.data.data_source.local.PostDao
import com.alex.assignmentfor4ksoft.feature_posts.data.data_source.remote.ImagesApi
import com.alex.assignmentfor4ksoft.feature_posts.data.data_source.remote.dto.ImageDto
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.feature_posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class PostRepositoryImpl(
    private val dao: PostDao,
    private val api: ImagesApi
) : PostRepository {

    override fun getPosts(): Flow<List<PostItem>> {
        return dao.getPosts()
    }

    override suspend fun getPostById(id: Int): PostItem? {
        return dao.getPostById(id)
    }

    override suspend fun insertPost(post: PostItem) {
        dao.insertPost(post)
    }

    override suspend fun deletePost(post: PostItem) {
        dao.deletePost(post)
    }

    override suspend fun getImages(limit: Int): Result<List<ImageDto>> {
        return try {
            val dto = api.getImages(
                limit = limit,
            )
            Result.success(dto)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}