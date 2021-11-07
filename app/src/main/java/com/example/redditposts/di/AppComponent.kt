package com.example.redditposts.di

import com.example.core.di.CoreComponent
import com.example.core.di.scopes.AppScope
import com.example.redditposts.RedditPostApplication
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
interface AppComponent {

    fun inject(application: RedditPostApplication)
}
