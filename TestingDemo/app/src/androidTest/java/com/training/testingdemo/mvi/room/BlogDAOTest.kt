package com.training.testingdemo.mvi.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.mvi.room.BlogCacheEntity
import com.example.mvi.room.BlogDAO
import com.example.mvi.room.BlogDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class BlogDAOTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Named("test_db")
    @Inject
    lateinit var blog_db: BlogDatabase

    private lateinit var blogDao: BlogDAO

    @Before
    fun setup(){
        hiltRule.inject()
        blogDao = blog_db.blogDAO()
    }

    @After
    fun close(){
        blog_db.close()
    }

    @Test
    fun insertBlogItem() = runBlockingTest {
        val blog = BlogCacheEntity(2,
            "Test",
            "Hi this is a testcase",
            "https//www.test.com/img",
            "testItem"
        )
        blogDao.insert(blog)

        val list = blogDao.get()
        assertThat(list).contains(blog)
    }

    @Test
    fun deleteBlogs() = runBlockingTest {
        val blog = BlogCacheEntity(2,
            "Test",
            "Hi this is a testcase",
            "https//www.test.com/img",
            "testItem"
        )
        blogDao.delete()
        val list = blogDao.get()
        assertThat(list.size).isEqualTo(0)
    }
}