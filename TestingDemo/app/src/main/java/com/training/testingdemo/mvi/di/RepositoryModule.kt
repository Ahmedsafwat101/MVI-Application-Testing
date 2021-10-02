package com.example.mvi.di

import com.example.mvi.repository.MainRepository
import com.example.mvi.retrofit.BlogRetrofit
import com.example.mvi.retrofit.NetworkMapper
import com.example.mvi.room.BlogDAO
import com.example.mvi.room.CacheMapper
import com.training.testingdemo.mvi.repository.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        retrofit: BlogRetrofit,
        blogDAO: BlogDAO,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): Repo {
         return MainRepository(
             blogDAO = blogDAO,
             retrofit = retrofit,
             cacheMapper = cacheMapper,
             networkMapper = networkMapper
         )
    }
}