package com.example.postfavorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.database.repositories.LocalPostRepository
import com.example.core.di.scopes.FeatureScope
import com.example.core.network.responses.Post
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@FeatureScope
class PostFavoriteViewModel  @Inject constructor(
    private val localPostRepository: LocalPostRepository
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

    fun loadPosts() {
        compositeDisposable.add(
            localPostRepository.getAllPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { postList ->
                        posts.value = postList.map { Post(
                            id = it.id,
                            title = it.title,
                            author = it.author,
                            isVideo = it.isVideo,
                            thumbnail = it.thumbnail) }
                    },
                    { throwable ->
                        Log.e("PostFavoriteFragment", throwable.message ?: "onError")
                    }
                )
        )
    }

    fun deletePost(post: Post) {
        Completable.fromRunnable {
            localPostRepository.deletePost(post)
        }
        .subscribeOn(Schedulers.io())
        .subscribe()
    }

    fun deleteAllPosts() {
        Completable.fromRunnable {
            localPostRepository.deleteAllPosts()
        }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}