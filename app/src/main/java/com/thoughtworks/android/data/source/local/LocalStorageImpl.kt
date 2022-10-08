package com.thoughtworks.android.data.source.local

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.android.R
import com.thoughtworks.android.common.Constants
import com.thoughtworks.android.data.model.Comment
import com.thoughtworks.android.data.model.Image
import com.thoughtworks.android.data.model.Sender
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.local.room.AppDatabase
import com.thoughtworks.android.data.source.local.room.model.CommentEntity
import com.thoughtworks.android.data.source.local.room.model.ImageEntity
import com.thoughtworks.android.data.source.local.room.model.SenderEntity
import com.thoughtworks.android.data.source.local.room.model.TweetEntity
import com.thoughtworks.android.utils.FileUtil
import com.thoughtworks.android.utils.SharedPreferenceUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.function.Consumer

class LocalStorageImpl(private val context: Context) : LocalStorage {

    companion object {
        private const val KEY_KNOWN = "known"
    }

    private val gson = Gson()
    private val appDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "practice-db").build()

    override var isHintShown: Boolean
        get() = SharedPreferenceUtil.readBoolean(
            context,
            Constants.SHARED_PREFERENCE_FILE,
            KEY_KNOWN,
            false
        )
        set(isShown) {
            SharedPreferenceUtil.writeBoolean(
                context,
                Constants.SHARED_PREFERENCE_FILE,
                KEY_KNOWN,
                isShown
            )
        }

    override fun getTweetsFromRaw(): List<Tweet> {
        val tweets = gson.fromJson<List<Tweet>>(
            FileUtil.getStringFromRaw(
                context, R.raw.tweets
            ), object : TypeToken<List<Tweet>>() {}.type
        )
        val filteredTweets: MutableList<Tweet> = mutableListOf()
        filterTweets(tweets, filteredTweets)
        return filteredTweets
    }

    private fun filterTweets(
        tweets: List<Tweet>,
        filteredTweets: MutableList<Tweet>
    ) {
        for (tweet in tweets) {
            if (tweet.error != null || tweet.unknownError != null) {
                continue
            }
            filteredTweets.add(tweet)
        }
    }

    override suspend fun updateTweets(tweets: List<Tweet>) {
        return withContext(Dispatchers.IO) {
            try {
                appDatabase.clearAllTables()
                appDatabase.runInTransaction {
                    tweets.forEach(Consumer { tweet: Tweet ->
                        val tweetEntity = toRoomTweet(tweet)
                        val tweetId = getIdByEntityAndInsertSender(tweet, tweetEntity)
                        insertImages(tweet, tweetId)
                        insertComment(tweet, tweetId)
                    })
                }
            } catch (_: Throwable) {
            }
        }
    }

    private fun insertComment(tweet: Tweet, tweetId: Long) {
        tweet.comments?.let { comments ->
            comments.forEach(Consumer { comment: Comment ->
                var senderId = 0L
                comment.sender?.let {
                    senderId = insertRoomSender(it)
                }
                val commentEntity = toRoomComment(
                    comment,
                    tweetId,
                    senderId
                )
                appDatabase.commentDao().insert(commentEntity)
            })
        }
    }

    private fun insertImages(tweet: Tweet, tweetId: Long) {
        tweet.images?.let { images ->
            images.forEach(Consumer { image: Image ->
                val imageEntity = toRoomImage(image, tweetId)
                appDatabase.imageDao().insert(imageEntity)
            })
        }
    }

    private fun getIdByEntityAndInsertSender(
        tweet: Tweet,
        tweetEntity: TweetEntity
    ): Long {
        tweet.sender?.let {
            tweetEntity.senderId = insertRoomSender(it)
        }
        return appDatabase.tweetDao().insert(tweetEntity)
    }

    override fun getTweets(): List<Tweet> {
        val tweets: MutableList<Tweet> = mutableListOf()
        appDatabase.tweetDao().getAll()
            .map { tweetEntity ->
                val senderEntities = appDatabase.senderDao().getAll()
                val imageEntities = appDatabase.imageDao().getAll()
                val commentEntities = appDatabase.commentDao().getAll()
                val tweet = toTweet(tweetEntity)
                senderEntities.find { it.id == tweetEntity.senderId }?.let {
                    tweet.sender = toSender(it)
                }
                filterImage(tweet, imageEntities, tweetEntity)
                filterComment(tweet, commentEntities, tweetEntity, senderEntities, tweets)
            }
        return tweets
    }

    private fun filterComment(
        tweet: Tweet,
        commentEntities: List<CommentEntity>,
        tweetEntity: TweetEntity,
        senderEntities: List<SenderEntity>,
        tweets: MutableList<Tweet>
    ) {
        tweet.comments = commentEntities.filter { it.tweetId == tweetEntity.id }
            .map { commentEntity ->
                val comment = Comment(
                    content = commentEntity.content
                )
                val senderEntity =
                    senderEntities.find { it.id == commentEntity.senderId }
                senderEntity?.let {
                    comment.sender = toSender(it)
                }

                comment
            }
        tweets.add(tweet)
    }

    private fun filterImage(
        tweet: Tweet,
        imageEntities: List<ImageEntity>,
        tweetEntity: TweetEntity
    ) {
        tweet.images =
            imageEntities.filter { it.tweetId == tweetEntity.id }.map { Image(it.url) }
    }

    private fun toTweet(tweetEntity: TweetEntity): Tweet {
        val tweet = Tweet()
        tweet.content = tweetEntity.content
        return tweet
    }

    private fun toSender(senderEntity: SenderEntity): Sender {
        val sender = Sender()
        sender.username = senderEntity.username
        sender.nick = senderEntity.nick
        sender.avatar = senderEntity.avatar
        return sender
    }

    private fun toRoomTweet(tweet: Tweet): TweetEntity {
        val tweetEntity = TweetEntity()
        tweetEntity.id = 0
        tweetEntity.content = tweet.content
        return tweetEntity
    }

    private fun insertRoomSender(sender: Sender): Long {
        val senderEntity = toRoomSender(sender)
        return appDatabase.senderDao().insert(senderEntity)
    }

    private fun toRoomSender(sender: Sender): SenderEntity {
        val senderEntity = SenderEntity()
        senderEntity.id = 0
        senderEntity.username = sender.username
        senderEntity.nick = sender.nick
        senderEntity.avatar = sender.avatar
        return senderEntity
    }

    private fun toRoomImage(image: Image, tweetId: Long): ImageEntity {
        val imageEntity = ImageEntity()
        imageEntity.id = 0
        imageEntity.tweetId = tweetId
        imageEntity.url = image.url
        return imageEntity
    }

    private fun toRoomComment(comment: Comment, tweetId: Long, senderId: Long): CommentEntity {
        val commentEntity = CommentEntity()
        commentEntity.id = 0
        commentEntity.tweetId = tweetId
        commentEntity.senderId = senderId
        commentEntity.content = comment.content
        return commentEntity
    }

}