package com.alex.assignmentfor4ksoft.feature_posts.presentation.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alex.assignmentfor4ksoft.databinding.ItemPostBinding
import com.alex.assignmentfor4ksoft.feature_posts.domain.entities.PostItem
import com.alex.assignmentfor4ksoft.utils.converters.DateTimeConverter
import com.alex.assignmentfor4ksoft.utils.diffutils.DiffListCallback
import com.bumptech.glide.Glide

class PostsAdapter(
    private var data: List<PostItem>,
    private val onItemClick: ((PostItem) -> Unit)? = null,
) : RecyclerView.Adapter<PostsAdapter.PostItemViewHolder>() {

    fun addData(item: PostItem) {
        val newList = data.toMutableList()
        newList.add(item)
        updateData(newList)
    }

    fun deleteData(item: PostItem) {
        val newList = data.toMutableList()
        newList.remove(item)
        updateData(newList)
    }

    private fun updateData(newData: List<PostItem>) {
        val diffCallback = DiffListCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data = ArrayList(newData)
        diffResult.dispatchUpdatesTo(this)
    }

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
                Glide.with(binding.ivPostItem.context).load(it.imageUrl).into(binding.ivPostItem)
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