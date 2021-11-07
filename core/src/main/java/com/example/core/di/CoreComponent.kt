package com.example.core.di

import android.content.Context
import com.example.core.database.postfavorite.PostFavoriteDao
import com.example.core.di.modules.ContextModule
import com.example.core.di.modules.DatabaseModule
import com.example.core.di.modules.NetworkModule
import com.example.core.network.services.PostService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        DatabaseModule::class
    ]
)
interface CoreComponent {

    fun context(): Context

    fun postService(): PostService

    //fun postRepository(): PostRepository

    fun postFavoriteDao(): PostFavoriteDao
}
