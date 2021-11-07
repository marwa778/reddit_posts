package com.example.postlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.network.repositories.PostRepository
import com.example.core.network.responses.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
class PostListViewModel @Inject constructor(
    private val postRepository: PostRepository
    ) : ViewModel() {

    private val posts: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>().also {
            loadPosts()
        }
    }

    private val compositeDisposable = CompositeDisposable()

    fun getPosts(): LiveData<List<Post>> {
        return posts
    }

    fun loadPosts(after:String = "") {
        compositeDisposable.add(
            postRepository.getPosts(after = after)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { postList ->
                        posts.value = postList.data.children.map { it.data }
                    },
                    { throwable ->
                        Log.e("PostListFragment", throwable.message ?: "onError")
                    }
                )
        )
    }

    fun searchPosts(searchQuery:String, after:String = "") {

    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
