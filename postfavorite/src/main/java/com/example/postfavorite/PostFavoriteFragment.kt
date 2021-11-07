package com.example.postfavorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.postfavorite.adapter.PostFavoriteAdapter
import javax.inject.Inject

class PostFavoriteFragment : Fragment() {

    @Inject
    lateinit var postFavoriteViewModel: PostFavoriteViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PostFavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)


        //val postListComponent = Dagg.create()
        //postListComponent.inject(this)

        postFavoriteViewModel.getPosts().observe(viewLifecycleOwner, { posts ->
            adapter.addPosts(posts)
        })
    }

    fun initializeViews(view: View) {
        recyclerView = view.findViewById(R.id.post_favorite_list)
        adapter = PostFavoriteAdapter(mutableListOf(), ::onClearClicked)
        recyclerView.adapter = adapter
    }

    fun onClearClicked(position: Int) {

    }

}