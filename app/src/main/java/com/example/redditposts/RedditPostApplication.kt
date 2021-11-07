package com.example.redditposts

import android.content.Context
import com.example.core.di.CoreComponent
import com.example.core.di.DaggerCoreComponent
import com.example.core.di.modules.ContextModule
import com.example.redditposts.di.DaggerAppComponent
import com.google.android.play.core.splitcompat.SplitCompatApplication

class RedditPostApplication : SplitCompatApplication() {
    lateinit var coreComponent: CoreComponent

    companion object {

        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as? RedditPostApplication)?.coreComponent
    }

    override fun onCreate() {
        super.onCreate()

        coreComponent = DaggerCoreComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()

        DaggerAppComponent.builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }
}
