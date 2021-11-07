package com.example.core.network.services

import com.example.core.network.responses.PostListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    @GET("/r/aww/top.json")
    fun getPosts(
        @Query("limit") limit: Int,
        @Query("after") after: String
    ) : Observable<PostListResponse>

    @GET("/r/aww/search.json")
    fun getPostsSearchResult(
        @Query("q") searchQuery: String,
        @Query("limit") limit: Int,
        @Query("after") after: String
    ) : Observable<PostListResponse>
}