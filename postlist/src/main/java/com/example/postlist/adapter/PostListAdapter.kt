package com.example.postlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.network.responses.Post
import com.example.postlist.R

class PostListAdapter(private val posts: MutableList<Post>,
                      private val onFavoriteClick: (Post) -> Unit
) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumbnailImage: AppCompatImageView = view.findViewById(R.id.post_item_thumbnail)
        val favoriteIcon: AppCompatImageView = view.findViewById(R.id.post_item_favorite)
        val playIcon: AppCompatImageView = view.findViewById(R.id.post_item_play)
        val titleTextView: AppCompatTextView = view.findViewById(R.id.post_item_title)
        val authorTextView: AppCompatTextView = view.findViewById(R.id.post_item_author)

        init {
            favoriteIcon.setOnClickListener {
                //onFavoriteClick()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.post_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.thumbnailImage.context)
            .load(posts[position].thumbnail)
            .into(viewHolder.thumbnailImage);
        //viewHolder.favoriteIcon.text = posts[position]
        viewHolder.playIcon.isVisible = posts[position].isVideo
        viewHolder.titleTextView.text = posts[position].title
        viewHolder.authorTextView.text = posts[position].author
    }

    override fun getItemCount() = posts.size
    
    fun addPosts(newPosts: List<Post>) {
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }
}
