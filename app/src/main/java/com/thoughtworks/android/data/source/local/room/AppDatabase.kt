package com.thoughtworks.android.data.source.local.room

import androidx.room.Database
import com.thoughtworks.android.data.source.local.room.model.ImageEntity
import com.thoughtworks.android.data.source.local.room.model.SenderEntity
import com.thoughtworks.android.data.source.local.room.model.CommentEntity
import com.thoughtworks.android.data.source.local.room.model.TweetEntity
import androidx.room.RoomDatabase
import com.thoughtworks.android.data.source.local.room.dao.ImageDao
import com.thoughtworks.android.data.source.local.room.dao.SenderDao
import com.thoughtworks.android.data.source.local.room.dao.CommentDao
import com.thoughtworks.android.data.source.local.room.dao.TweetDao

@Database(
    entities = [ImageEntity::class, SenderEntity::class, CommentEntity::class, TweetEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun senderDao(): SenderDao
    abstract fun commentDao(): CommentDao
    abstract fun tweetDao(): TweetDao
}