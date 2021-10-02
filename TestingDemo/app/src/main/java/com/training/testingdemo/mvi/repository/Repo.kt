package com.training.testingdemo.mvi.repository

import com.example.mvi.Model.Blog
import com.example.mvi.room.BlogCacheEntity
import com.example.mvi.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface Repo {

    suspend fun getBlogs(): Flow<DataState<List<Blog>>>
    suspend fun insertData(blog: Blog)
    suspend fun getData():List<Blog>
}