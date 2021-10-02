package com.example.mvi.di

import android.content.Context
import androidx.room.Room
import com.example.mvi.Constants.DATABASE_NAME
import com.example.mvi.room.BlogDAO
import com.example.mvi.room.BlogDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides

    fun provideBlogDB(@ApplicationContext context:Context ):BlogDatabase{
        return Room.databaseBuilder(
            context,
            BlogDatabase::class.java,
            DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBlogDAO(blogDatabase: BlogDatabase):BlogDAO{
       return blogDatabase.blogDAO()
    }
}