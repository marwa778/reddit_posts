package com.example.core.database.postfavorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostFavorite(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "is_video") val isVideo: Boolean,
    @ColumnInfo(name = "thumbnail") val thumbnail: String
)
