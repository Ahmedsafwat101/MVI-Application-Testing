package com.example.mvi.di

import com.example.mvi.Constants.BASE_URL
import com.example.mvi.retrofit.BlogRetrofit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * ApplicationComponent being renamed to SingletonComponent,
 * to allow usage of Hilt in non-Android Gradle modules
 **/

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {


    // Parsing json data into an Java object
    @Singleton
    @Provides
    fun provideGSONBuilder():Gson{
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson):Retrofit.Builder{
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder):BlogRetrofit{
        return retrofit
            .build()
            .create(BlogRetrofit::class.java)
    }



}