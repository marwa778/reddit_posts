package com.example.core.database.postfavorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface PostFavoriteDao {
    @Query("SELECT * FROM PostFavorite")
    fun getAll(): Observable<List<PostFavorite>>

    @Query("SELECT * FROM PostFavorite WHERE id = :postFavoriteId")
    fun getPostById(postFavoriteId: String): PostFavorite?

    @Insert
    fun insertPost(post: PostFavorite)

    @Delete
    fun deletePost(post: PostFavorite)

    @Query("DELETE FROM PostFavorite")
    fun deleteAll()
}
