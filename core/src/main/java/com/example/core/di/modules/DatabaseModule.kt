package com.example.core.di.modules

import android.content.Context
import androidx.room.Room
import com.example.core.BuildConfig
import com.example.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providePostDatabase(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            BuildConfig.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun providePostFavoriteDao(appDatabase: AppDatabase) =
        appDatabase.postFavoriteDao()
}