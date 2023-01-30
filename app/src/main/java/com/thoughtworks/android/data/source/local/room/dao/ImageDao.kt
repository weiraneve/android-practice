package com.thoughtworks.android.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thoughtworks.android.data.source.local.room.model.ImageEntity

@Dao
interface ImageDao {
    
    @Query("SELECT * FROM image")
    fun getAll(): List<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(imageEntity: ImageEntity): Long
}