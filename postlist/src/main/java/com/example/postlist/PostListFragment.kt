package com.example.postlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.postlist.adapter.PostListAdapter
import com.example.postlist.di.DaggerPostListComponent
import com.example.redditposts.RedditPostApplication.Companion.coreComponent
import javax.inject.Inject

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

        postListViewModel.getPosts().observe(viewLifecycleOwner, { posts ->
            adapter.addPosts(posts)
        })
    }

    fun initializeViews(view: View) {
        recyclerView = view.findViewById(R.id.post_list)
        adapter = PostListAdapter(mutableListOf(), ::onFavoriteClicked)
        recyclerView.adapter = adapter
    }

    fun onFavoriteClicked(position: Int) {

    }
}