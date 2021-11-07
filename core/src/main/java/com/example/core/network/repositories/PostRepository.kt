package com.example.core.network.repositories

import com.example.core.network.responses.PostListResponse
import com.example.core.network.services.PostService
import io.reactivex.Observable
import javax.inject.Inject

private const val QUERY_LIMIT = 10

class PostRepository @Inject constructor(internal val service: PostService) {

    fun getPosts(limit:Int  = QUERY_LIMIT, after: String = "") : Observable<PostListResponse> {
        return service.getPosts(limit, after)
    }

    fun getPostsSearchResult(searchQuery:String, limit:Int  = QUERY_LIMIT, after: String = "") : Observable<PostListResponse> {
        return service.getPostsSearchResult(searchQuery, limit, after)
    }
}