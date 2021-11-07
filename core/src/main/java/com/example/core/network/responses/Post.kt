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
    @SerializedName("id") @Expose val id:String = "",
    val isFavorite:Boolean = false,
)
