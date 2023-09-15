package com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.AddEditPostEvent
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.AddEditPostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RowColors(
    viewModel: AddEditPostViewModel,
    scope: CoroutineScope,
    backgroundAnimatable: Animatable<Color, AnimationVector4D>,
    isStateChanged: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PostItem.postColors.forEach { color ->
            val colorInt = color.toArgb()
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(15.dp, CircleShape)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = 3.dp,
                        color = if (viewModel.postColor.value == colorInt) {
                            Color.Black
                        } else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable {
                        scope.launch {
                            backgroundAnimatable.animateTo(
                                targetValue = Color(colorInt),
                                animationSpec = tween(
                                    durationMillis = 500
                                )
                            )
                        }
                        viewModel.onEvent(AddEditPostEvent.ChangeColor(colorInt))
                        isStateChanged.value = true
                    }
            )
        }
    }
}