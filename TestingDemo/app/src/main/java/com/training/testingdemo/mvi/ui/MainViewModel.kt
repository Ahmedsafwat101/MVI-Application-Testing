package com.example.mvi.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.mvi.Model.Blog
import com.example.mvi.repository.MainRepository
import com.example.mvi.util.DataState
import com.training.testingdemo.mvi.repository.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val mainRepository: Repo,
) : ViewModel() {
    var finished = ""
    val scope = viewModelScope
    private val _dataState: MutableStateFlow<DataState<List<Blog>>> =
        MutableStateFlow(DataState.Loading)

    val dataState: StateFlow<DataState<List<Blog>>>
        get() = _dataState


    suspend fun setStateEvent(mainStateEvent: MainStateEvent) {
        when (mainStateEvent) {
            is MainStateEvent.GetBlogEvent -> {
                mainRepository.getBlogs().onEach {
                    _dataState.emit(it)
                    finished = _dataState.value::class.java.toString()
                }.launchIn(viewModelScope)
            }
            is MainStateEvent.None -> {
                //Skip
            }
        }
    }

}

sealed class MainStateEvent() {
    object GetBlogEvent : MainStateEvent()
    object None : MainStateEvent()
}