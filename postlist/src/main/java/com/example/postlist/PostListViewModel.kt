package com.example.postlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.database.postfavorite.PostFavorite
import com.example.core.database.repositories.LocalPostRepository
import com.example.core.di.scopes.FeatureScope
import com.example.core.network.repositories.PostRepository
import com.example.core.network.responses.Post
import com.example.core.network.responses.PostListResponse
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.Observable

@FeatureScope
class PostListViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val localPostRepository: LocalPostRepository,
    ) : ViewModel() {

    private val posts: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>().also {
            loadPosts()
        }
    }

    private val compositeDisposable = CompositeDisposable()
    var afterSearchQuery = ""

    fun getPosts(): LiveData<List<Post>> {
        return posts
    }

    fun loadPosts(after:String = afterSearchQuery) {
        compositeDisposable.add(
            getPostList(postRepository.getPosts(after = after))
        )
    }

    fun searchPosts(searchQuery:String, after:String = afterSearchQuery) {
        compositeDisposable.add(
            getPostList(postRepository.getPostsSearchResult(searchQuery = searchQuery, after = after))
        )
    }

    private fun getPostList(postList: Observable<PostListResponse>) =
        postList.map { posts ->
            afterSearchQuery = posts.data.after
            posts.data.children.map { it.data } }
            .flatMapIterable { posts -> posts }
            .flatMap { post ->
                getPost(localPostRepository.getPostById(post.id), post)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { post ->
                    posts.value = listOf( Post(
                        id = post.id,
                        title = post.title,
                        author = post.author,
                        isVideo = post.isVideo,
                        isFavorite = post.isFavorite,
                        thumbnail = post.thumbnail))
                },
                { throwable ->
                    Log.e("PostListFragment", throwable.message ?: "onError")
                })

    private fun getPost(localPost: PostFavorite?, remotePost: Post) : Observable<Post> {
        return if(localPost != null) {
            Observable.just(remotePost.copy(isFavorite = true))
        } else
            Observable.just(remotePost)
    }

    fun onFavoriteClicked(post: Post) {
        if(post.isFavorite)
            deletePost(post)
        else
            insertPost(post)
    }

    fun deletePost(post: Post) {
        Completable.fromRunnable {
            localPostRepository.deletePost(post)
        }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun insertPost(post: Post) {
        Completable.fromRunnable {
            localPostRepository.insertPost(post)
        }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
