package com.training.testingdemo.mvi.repository

import android.util.Log
import com.example.mvi.Model.Blog
import com.example.mvi.util.DataState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.Exception

class FakeMainRepository() : Repo {

    private val list = mutableListOf<Blog>()
    private val onlineList = mutableListOf<Blog>()
    private var shouldReturnError = false

    fun setShouldReturnError(bool: Boolean) {
        shouldReturnError = bool
    }

    override suspend fun getBlogs(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        try {
            initList()
            tryError()

            for (blog in onlineList) {
                insertData(blog)
            }
            val l = getData()
            emit(DataState.Success(l))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun insertData(blog: Blog) {
        list.add(blog)
    }

    override suspend fun getData(): List<Blog> {
        return list
    }

    private fun tryError() {
        if (shouldReturnError) {
            throw Exception()
        }
    }

    private fun initList() {
        onlineList.apply {
            add(
                Blog(
                    2,
                    "Test2",
                    "Hi this is a testcase2",
                    "https//www.test.com/img",
                    "testItem"
                )
            )

            add(
                Blog(
                    3,
                    "Test3",
                    "Hi this is a testcase3",
                    "https//www.test.com/img",
                    "testItem"
                )
            )

            add(
                Blog(
                    4,
                    "Test4",
                    "Hi this is a testcase4",
                    "https//www.test.com/img",
                    "testItem"
                )
            )

            add(
                Blog(
                    5,
                    "Test5",
                    "Hi this is a testcase5",
                    "https//www.test.com/img",
                    "testItem"
                )
            )
        }
    }

}