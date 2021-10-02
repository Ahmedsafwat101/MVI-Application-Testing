package com.example.mvi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.mvi.Model.Blog
import com.example.mvi.util.DataState
import com.training.testingdemo.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.StringBuilder

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val viewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeObserver()
        viewModel.scope.launch {
            viewModel.setStateEvent(MainStateEvent.GetBlogEvent)
        }
    }

    private fun subscribeObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.dataState.collect {
                when(it){
                    is DataState.Success<List<Blog>> ->{
                        displayProgressbar(false)
                        displayTitles(it.data)
                    }
                    is DataState.Error ->{
                        displayProgressbar(false)
                        displayError(it.exception.message.toString())
                    }
                    is DataState.Loading ->{
                        displayProgressbar(true)
                    }
                }
            }
        }
    }

    private fun displayError(message:String){
        if(message!=null){
            text.text = message
        }else{
            text.text = "Unknown Error!"
        }
    }

    private fun displayProgressbar(isDisplayed:Boolean){
        progress_bar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayTitles(blogs:List<Blog>){
        val stringBuilder = StringBuilder()
        for(blog in blogs){
            stringBuilder.append(blog.title+"\n")
        }
        text.text = stringBuilder.toString()
    }


}