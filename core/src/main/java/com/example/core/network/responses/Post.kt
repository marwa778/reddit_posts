package com.example.core.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostData(
    val data: Post = Post()
)

data class Post (
    @SerializedName("title") @Expose val title:String = "",
    @SerializedName("author") @Expose val author:String = "",
    @SerializedName("is_video") @Expose val isVideo: Boolean = false,
    @SerializedName("thumbnail") @Expose val thumbnail:String = "",
    @SerializedName("url") @Expose val url:String = "",
    @SerializedName("num_comments") @Expose val numOfComments:Int = 0,
    @SerializedName("score") @Expose val score:Int = 0
)
