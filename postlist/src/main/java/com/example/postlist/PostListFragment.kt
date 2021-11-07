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
import androidx.appcompat.widget.SearchView
import java.lang.Exception

class PostListFragment : Fragment() {

    @Inject
    lateinit var postListViewModel: PostListViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PostListAdapter
    lateinit var searchView: SearchView

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
        addSearchViewListener()

        postListViewModel.getPosts().observe(viewLifecycleOwner, { posts ->
            adapter.addPosts(posts)
        })
    }

    fun initializeViews(view: View) {
        recyclerView = view.findViewById(R.id.post_list)
        searchView = view.findViewById(R.id.post_list_search)
        adapter = PostListAdapter(mutableListOf(), ::onFavoriteClicked)
        recyclerView.adapter = adapter
    }

    fun addScrollStateChangeListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if(postListViewModel.query.isNotBlank()) {
                        postListViewModel.searchPosts()
                    } else {
                        postListViewModel.loadPosts()
                    }
                }
            }
        })
    }

    fun addSearchViewListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                try {
                    adapter.clearPosts()
                    postListViewModel.searchPosts(searchQuery = query, after = "")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.isBlank()) {
                    adapter.clearPosts()
                    postListViewModel.loadPosts(after = "")
                }
                return false
            }
        })
    }

    fun onFavoriteClicked(post: Post, position: Int) {
        postListViewModel.onFavoriteClicked(post)
        adapter.setFavorite(position)
    }
}