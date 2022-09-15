package com.thoughtworks.android.data.source.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tweet",
    foreignKeys = [ForeignKey(
        entity = SenderEntity::class,
        parentColumns = ["id"],
        childColumns = ["sender_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
class TweetEntity {

    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @JvmField
    @ColumnInfo(name = "sender_id", index = true)
    var senderId: Long = 0

    @JvmField
    @ColumnInfo(name = "content")
    var content: String? = null
}