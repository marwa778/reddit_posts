package com.example.postfavorite.di

import com.example.core.di.CoreComponent
import com.example.core.di.modules.NetworkModule
import com.example.core.di.scopes.FeatureScope
import com.example.postfavorite.PostFavoriteFragment
import dagger.Component
import javax.inject.Singleton

@FeatureScope
@Component(
    dependencies = [CoreComponent::class]
)
interface PostFavoriteComponent {
    fun inject(postFavoriteFragment: PostFavoriteFragment)
}