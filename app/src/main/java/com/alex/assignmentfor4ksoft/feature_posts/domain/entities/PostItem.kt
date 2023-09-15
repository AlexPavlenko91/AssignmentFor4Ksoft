package com.alex.assignmentfor4ksoft.feature_posts.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alex.assignmentfor4ksoft.core.domain.model.DiffModel
import com.alex.assignmentfor4ksoft.ui.theme.BabyBlue
import com.alex.assignmentfor4ksoft.ui.theme.LightGreen
import com.alex.assignmentfor4ksoft.ui.theme.RedOrange
import com.alex.assignmentfor4ksoft.ui.theme.RedPink
import com.alex.assignmentfor4ksoft.ui.theme.Violet

@Entity(
    tableName = "posts"
)
data class PostItem(
    val imageUrl: String,
    val comment: String,
    val dateTime: Long?,
    val color: Int,
    @PrimaryKey val id: Int? = null,
) : DiffModel<PostItem> {

    companion object {
        val postColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)

    }

    override fun areItemsTheSame(newItem: PostItem) = this.id == newItem.id
    override fun areContentsTheSame(newItem: PostItem) = this == newItem
}

class InvalidPostException(message: String): Exception(message)