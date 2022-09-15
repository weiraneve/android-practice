package com.thoughtworks.android.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thoughtworks.android.data.source.local.room.model.SenderEntity
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface SenderDao {

    @Query("SELECT * FROM sender")
    fun getAll(): Flowable<List<SenderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(senderEntity: SenderEntity): Single<Long>
}