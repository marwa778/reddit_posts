package com.example.core.database.repositories

import com.example.core.database.postfavorite.PostFavorite
import com.example.core.database.postfavorite.PostFavoriteDao
import com.example.core.network.responses.Post
import io.reactivex.Observable
import javax.inject.Inject

class LocalPostRepository @Inject constructor(
    internal val postFavoriteDao: PostFavoriteDao) {

    fun getAllPosts() : Observable<List<PostFavorite>> = postFavoriteDao.getAll()

    fun insertPost(post: Post) {
        postFavoriteDao.insertPost(mapPostToFavorite(post))
    }

    fun deletePost(post: Post) {
        postFavoriteDao.delete(mapPostToFavorite(post))
    }

    fun deleteAllPosts() {
        postFavoriteDao.deleteAll()
    }

    private fun mapPostToFavorite(post: Post) =
        PostFavorite(
            id = post.id,
            title = post.title,
            author = post.author,
            isVideo = post.isVideo,
            thumbnail = post.thumbnail
        )
}