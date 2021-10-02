package com.example.mvi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BlogDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(blogEntity:BlogCacheEntity):Long

    @Query("Select * from blogs_table")
    suspend fun get():List<BlogCacheEntity>

    @Query("Delete from blogs_table")
    suspend fun delete()
}
