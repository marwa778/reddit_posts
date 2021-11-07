package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.database.postfavorite.PostFavorite
import com.example.core.database.postfavorite.PostFavoriteDao

@Database(entities = [PostFavorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postFavoriteDao(): PostFavoriteDao
}
