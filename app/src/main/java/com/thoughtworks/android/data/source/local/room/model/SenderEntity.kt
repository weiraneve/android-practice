package com.thoughtworks.android.data.source.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sender")
class SenderEntity {

    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @JvmField
    @ColumnInfo(name = "username")
    var username: String? = null

    @JvmField
    @ColumnInfo(name = "nick")
    var nick: String? = null

    @JvmField
    @ColumnInfo(name = "avatar")
    var avatar: String? = null
}