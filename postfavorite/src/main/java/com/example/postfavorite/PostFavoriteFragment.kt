package com.example.postfavorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.core.network.responses.Post
import com.example.postfavorite.adapter.PostFavoriteAdapter
import com.example.postfavorite.di.DaggerPostFavoriteComponent
import com.example.redditposts.RedditPostApplication.Companion.coreComponent
import javax.inject.Inject

class PostFavoriteFragment : Fragment() {

    @Inject
    lateinit var postFavoriteViewModel: PostFavoriteViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PostFavoriteAdapter
    lateinit var clearListBtn: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)

        val postFavoriteComponent = DaggerPostFavoriteComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .build()
        postFavoriteComponent.inject(this)

        postFavoriteViewModel.getPosts().observe(viewLifecycleOwner, { posts ->
            adapter.addPosts(posts)
        })

        postFavoriteViewModel.notifyChange.observe(viewLifecycleOwner, { _ ->
            adapter.notifyDataSetChanged()
        })
    }

    private fun initializeViews(view: View) {
        recyclerView = view.findViewById(R.id.post_favorite_list)
        adapter = PostFavoriteAdapter(mutableListOf(), ::onClearClicked)
        recyclerView.adapter = adapter

        clearListBtn = view.findViewById(R.id.post_favorite_clear_list)
        clearListBtn.setOnClickListener {
            postFavoriteViewModel.deleteAllPosts()
        }
    }

    fun onClearClicked(post: Post) {
        postFavoriteViewModel.deletePost(post)
    }
}
