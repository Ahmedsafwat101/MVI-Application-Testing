package com.training.testingdemo.mvi.ui

import android.util.Log
import com.example.mvi.ui.MainStateEvent
import com.example.mvi.ui.MainViewModel
import com.example.mvi.util.DataState
import com.training.testingdemo.mvi.repository.FakeMainRepository
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

@ExperimentalCoroutinesApi
class MainViewModelTest{

    private lateinit var viewModel:MainViewModel
    private lateinit var repository: FakeMainRepository

    @Before
    fun setup(){
        repository = FakeMainRepository()
        viewModel = MainViewModel(repository)
    }

    @Test
    fun testStateSuccess(){
        repository.setShouldReturnError(false)
        runBlockingTest {
            viewModel.setStateEvent(MainStateEvent.GetBlogEvent)
        }
        val state = viewModel.dataState
        assertThat(state.value::class.java).isEqualTo(DataState.Success::class.java)
    }

    @Test
    fun testStateFailed(){
        repository.setShouldReturnError(true)
        val x = runBlockingTest {
            viewModel.setStateEvent(MainStateEvent.GetBlogEvent)
        }

        val state = viewModel.dataState
        assertThat(state.value::class.java).isEqualTo(DataState.Error::class.java)
    }

    @Test
    fun testStateLoading(){
        val state = viewModel.dataState
        assertThat(state.value::class.java).isEqualTo(DataState.Loading::class.java)
    }



}