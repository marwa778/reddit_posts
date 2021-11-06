package com.example.core.network.responses

data class PostListResponse (
    val data: DataResponse = DataResponse()
)

data class DataResponse (
    val after: String = "",
    val children: List<PostData> = listOf()
)