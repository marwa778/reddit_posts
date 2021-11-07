package com.example.postlist.di

import com.example.core.di.modules.NetworkModule
import com.example.postlist.PostListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface PostListComponent {
    fun inject(postListFragment: PostListFragment)
}
