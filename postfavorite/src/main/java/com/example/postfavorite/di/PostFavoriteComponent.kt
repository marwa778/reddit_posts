package com.example.postfavorite.di

import com.example.core.di.modules.NetworkModule
import com.example.postfavorite.PostFavoriteFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface PostFavoriteComponent {
    fun inject(postFavoriteFragment: PostFavoriteFragment)
}