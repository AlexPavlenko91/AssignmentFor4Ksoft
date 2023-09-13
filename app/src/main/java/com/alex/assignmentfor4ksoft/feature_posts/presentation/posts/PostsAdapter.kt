package com.alex.assignmentfor4ksoft.feature_posts.presentation.posts

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex.assignmentfor4ksoft.databinding.ItemPostBinding
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.utils.converters.DateTimeConverter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class PostsAdapter(
    private var data: List<PostItem>,
    private val onItemClick: ((PostItem) -> Unit)? = null,
) : RecyclerView.Adapter<PostsAdapter.PostItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val binding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val postItem = data[position]
        holder.bind(postItem)
    }

    inner class PostItemViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PostItem?) {
            item?.let {
                binding.tvComment.text = it.comment
                binding.tvDate.text = DateTimeConverter.convertLongToTime(it.dateTime)
                Glide.with(binding.ivPostItem.context)
                    .load(it.imageUrl)
                    .transform(CenterInside(), RoundedCorners(24))
                    .into(binding.ivPostItem)

                val shape = GradientDrawable()
                shape.cornerRadius = 24f
                shape.setColor(it.color)
                binding.root.background = shape

                setupListeners(item)
            }
        }

        private fun setupListeners(item: PostItem) {
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }
}