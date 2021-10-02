package com.example.mvi.repository

import com.example.mvi.Model.Blog
import com.example.mvi.retrofit.BlogRetrofit
import com.example.mvi.retrofit.NetworkMapper
import com.example.mvi.room.BlogCacheEntity
import com.example.mvi.room.BlogDAO
import com.example.mvi.room.CacheMapper
import com.example.mvi.util.DataState
import com.training.testingdemo.mvi.repository.Repo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepository
constructor(
    private val retrofit: BlogRetrofit,
    private val blogDAO: BlogDAO,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
): Repo{
    override suspend fun getBlogs():Flow<DataState<List<Blog>>> = flow{
        emit(DataState.Loading)
        delay(1000) // just to show the progress bar
        try {
            //Fetch Data from URL
            val networkEntities = retrofit.getBlogs() //  returned List<BlogNetworkEntity>
            //Map Data from BlogNetworkEntity to JavaModel (Domain Object)
            val blogs = networkMapper.mapFromEntityList(networkEntities) // map from List<BlogNetworkEntity> to List<Blog>
            //Cache Data in Room Database
            for(blog in blogs){
                //Map JavaModel to BlogCacheEntity
                insertData(blog)
            }
            val BlogList = getData()
            emit(DataState.Success(BlogList))
        }catch (e:Exception){
           emit(DataState.Error(e))
        }
    }

    override suspend fun insertData(blog: Blog){
        blogDAO.insert(cacheMapper.mapFromDomain(blog))
    }

    override suspend fun getData():List<Blog>{
        return cacheMapper.mapFromEntityList(blogDAO.get())
    }
}