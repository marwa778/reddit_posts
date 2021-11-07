package com.example.postlist.di

import com.example.core.di.CoreComponent
import com.example.core.di.scopes.FeatureScope
import com.example.postlist.PostListFragment
import dagger.Component

@FeatureScope
@Component(dependencies = [CoreComponent::class])
interface PostListComponent {
    fun inject(postListFragment: PostListFragment)
}
