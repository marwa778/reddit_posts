package com.example.redditposts.di

import com.example.redditposts.RedditPostApplication
import dagger.Component

@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    fun inject(application: RedditPostApplication)
}
