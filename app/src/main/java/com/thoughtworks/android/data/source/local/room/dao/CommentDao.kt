package com.thoughtworks.android.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thoughtworks.android.data.source.local.room.model.CommentEntity

@Dao
interface CommentDao {

    @Query("SELECT * FROM comment")
    fun getAll(): List<CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(commentEntity: CommentEntity): Long
}