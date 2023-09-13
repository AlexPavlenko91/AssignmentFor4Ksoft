package com.alex.assignmentfor4ksoft.feature_posts.data.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    fun getPosts(): Flow<List<PostItem>>

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getPostById(id: Int): PostItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostItem)

    @Delete
    suspend fun deletePost(post: PostItem)
}