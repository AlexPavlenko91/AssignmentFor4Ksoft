package com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.components

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alex.assignmentfor4ksoft.R
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.AddEditPostEvent
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.AddEditPostViewModel
import com.alex.assignmentfor4ksoft.utils.UiText
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun BoxImages(
    viewModel: AddEditPostViewModel,
    shouldShowDialog: MutableState<Boolean>,
    context: Context
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                state = rememberLazyListState()
            ) {
                items(viewModel.stateImages.images) { item ->
                    val currentUrl = item.downloadUrl
                    Box(
                        modifier = Modifier
                            .height(144.dp)
                            .width(200.dp)
                            .padding(16.dp)
                            .shadow(15.dp, RoundedCornerShape(10.dp))
                            .border(
                                width = if (viewModel.imageUrl.value == currentUrl) 3.dp else 0.dp,
                                Color.Black,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                viewModel.onEvent(AddEditPostEvent.SelectImage(item.downloadUrl))
                                shouldShowDialog.value = true
                            }
                    ) {
                        GlideImage(
                            model = item.downloadUrl,
                            contentDescription = UiText.StringResource(R.string.image_content_descr)
                                .asString(context),
                        )
                    }
                }

            }
        }
    }
}