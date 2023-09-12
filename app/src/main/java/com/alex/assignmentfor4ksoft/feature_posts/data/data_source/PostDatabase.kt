package com.alex.assignmentfor4ksoft.feature_posts.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem

@Database(
    entities = [PostItem::class],
    version = 1
)
abstract class PostDatabase: RoomDatabase() {

    abstract val postDao: PostDao

    companion object {
        const val DATABASE_NAME = "posts_db"
    }
}