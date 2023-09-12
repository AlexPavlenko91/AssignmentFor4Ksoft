package com.alex.assignmentfor4ksoft.feature_posts.data.repository

import com.alex.assignmentfor4ksoft.feature_posts.data.data_source.PostDao
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.feature_posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class PostRepositoryImpl(
    private val dao: PostDao
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
}