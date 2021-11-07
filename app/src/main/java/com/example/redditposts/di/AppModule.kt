package com.example.redditposts.di

import android.content.Context
import com.example.redditposts.RedditPostApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: RedditPostApplication): Context = application.applicationContext
}
