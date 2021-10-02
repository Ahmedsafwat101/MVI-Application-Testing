package com.training.testingdemo.di

import android.content.Context
import androidx.room.Room
import com.example.mvi.Constants
import com.example.mvi.room.BlogDAO
import com.example.mvi.room.BlogDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideBlogDB(@ApplicationContext context: Context): BlogDatabase {
        return Room.databaseBuilder(
            context,
            BlogDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}