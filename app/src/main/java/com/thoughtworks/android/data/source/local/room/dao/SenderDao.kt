package com.thoughtworks.android.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thoughtworks.android.data.source.local.room.model.SenderEntity

@Dao
interface SenderDao {

    @Query("SELECT * FROM sender")
    fun getAll(): List<SenderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(senderEntity: SenderEntity): Long
}