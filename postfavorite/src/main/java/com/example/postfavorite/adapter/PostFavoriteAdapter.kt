package com.example.postfavorite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.network.responses.Post
import com.example.postfavorite.R

class PostFavoriteAdapter(private val posts: MutableList<Post>,
                          val onClearClick: (Post) -> Unit
) : RecyclerView.Adapter<PostFavoriteAdapter.ViewHolder>() {

    class ViewHolder(view: View, posts: List<Post>, onClearClick: (Post) -> Unit) : RecyclerView.ViewHolder(view) {
        val thumbnailImage: AppCompatImageView = view.findViewById(R.id.post_item_thumbnail)
        val clearIcon: AppCompatImageView = view.findViewById(R.id.post_item_clear)
        val playIcon: AppCompatImageView = view.findViewById(R.id.post_item_play)
        val titleTextView: AppCompatTextView = view.findViewById(R.id.post_item_title)

        init {
            clearIcon.setOnClickListener {
                onClearClick(posts[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.post_favorite_item, viewGroup, false)

        return ViewHolder(view, posts, onClearClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.thumbnailImage.context)
            .load(posts[position].thumbnail)
            .into(viewHolder.thumbnailImage)
        viewHolder.playIcon.isVisible = posts[position].isVideo
        viewHolder.titleTextView.text = posts[position].title
    }

    override fun getItemCount() = posts.size

    fun addPosts(newPosts: List<Post>) {
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }
}
