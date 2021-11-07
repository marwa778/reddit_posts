package com.example.postlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.network.responses.Post
import com.example.postlist.adapter.PostListAdapter
import com.example.postlist.di.DaggerPostListComponent
import com.example.redditposts.RedditPostApplication.Companion.coreComponent
import javax.inject.Inject
import android.widget.Toast

class PostListFragment : Fragment() {

    @Inject
    lateinit var postListViewModel: PostListViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PostListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)

        val postListComponent = DaggerPostListComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .build()
        postListComponent.inject(this)

        addScrollStateChangeListener()

        postListViewModel.getPosts().observe(viewLifecycleOwner, { posts ->
            adapter.addPosts(posts)
        })
    }

    fun initializeViews(view: View) {
        recyclerView = view.findViewById(R.id.post_list)
        adapter = PostListAdapter(mutableListOf(), ::onFavoriteClicked)
        recyclerView.adapter = adapter
    }

    fun addScrollStateChangeListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    postListViewModel.loadPosts()
                }
            }
        })
    }

    fun onFavoriteClicked(post: Post) {
        postListViewModel.onFavoriteClicked(post)
        adapter.notifyDataSetChanged()
    }
}