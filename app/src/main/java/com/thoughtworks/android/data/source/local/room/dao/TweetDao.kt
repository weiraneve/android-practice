package com.thoughtworks.android.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thoughtworks.android.data.source.local.room.model.TweetEntity

@Dao
interface TweetDao {

    @Query("SELECT * FROM tweet")
    fun getAll(): List<TweetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tweetEntity: TweetEntity): Long
}