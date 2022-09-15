package com.thoughtworks.android.data.source.local

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.android.R
import com.thoughtworks.android.data.model.Comment
import com.thoughtworks.android.data.model.Image
import com.thoughtworks.android.data.model.Sender
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.local.room.AppDatabase
import com.thoughtworks.android.data.source.local.room.model.CommentEntity
import com.thoughtworks.android.data.source.local.room.model.ImageEntity
import com.thoughtworks.android.data.source.local.room.model.SenderEntity
import com.thoughtworks.android.data.source.local.room.model.TweetEntity
import com.thoughtworks.android.common.Constants
import com.thoughtworks.android.utils.FileUtil
import com.thoughtworks.android.utils.SharedPreferenceUtil
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import java.util.function.Consumer

class LocalStorageImpl(private val context: Context) : LocalStorage {

    companion object {
        private const val KEY_KNOWN = "known"
    }

    private val gson = Gson()
    private val appDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "practice-db").build()

    override var isKnown: Boolean
        get() = SharedPreferenceUtil.readBoolean(
            context,
            Constants.SHARED_PREFERENCE_FILE,
            KEY_KNOWN,
            false
        )
        set(isKnown) {
            SharedPreferenceUtil.writeBoolean(
                context,
                Constants.SHARED_PREFERENCE_FILE,
                KEY_KNOWN,
                isKnown
            )
        }

    override fun getTweetsFromRaw(): List<Tweet> {
        val tweets = gson.fromJson<List<Tweet>>(
            FileUtil.getStringFromRaw(
                context, R.raw.tweets
            ), object : TypeToken<List<Tweet>>() {}.type
        )
        val filteredTweets: MutableList<Tweet> = mutableListOf()
        for (tweet in tweets) {
            if (tweet.error != null || tweet.unknownError != null) {
                continue
            }
            filteredTweets.add(tweet)
        }
        return filteredTweets
    }

    override fun updateTweets(tweets: List<Tweet>): Single<Boolean> {
        return Single.create { emitter: SingleEmitter<Boolean> ->
            try {
                appDatabase.clearAllTables()
                appDatabase.runInTransaction {
                    tweets.forEach(Consumer { tweet: Tweet ->
                        val tweetEntity = toRoomTweet(tweet)
                        tweet.sender?.let {
                            tweetEntity.senderId = insertRoomSender(it)
                        }
                        val tweetId = appDatabase.tweetDao().insert(tweetEntity).blockingGet()

                        tweet.images?.let { images ->
                            images.forEach(Consumer { image: Image ->
                                val imageEntity = toRoomImage(image, tweetId)
                                appDatabase.imageDao().insert(imageEntity).blockingGet()
                            })
                        }

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

                                appDatabase.commentDao().insert(commentEntity).blockingGet()
                            })
                        }
                    })
                }
            } catch (t: Throwable) {
                emitter.onError(t)
                return@create
            }
            emitter.onSuccess(true)
        }
    }

    override fun getTweets(): Flowable<List<Tweet>> {
        return appDatabase.tweetDao().getAll()
            .map { tweetEntities: List<TweetEntity> ->
                val senderEntities = appDatabase.senderDao().getAll().blockingFirst()
                val imageEntities = appDatabase.imageDao().getAll().blockingFirst()
                val commentEntities = appDatabase.commentDao().getAll().blockingFirst()
                val tweets: MutableList<Tweet> = mutableListOf()
                for (tweetEntity in tweetEntities) {
                    val tweet = toTweet(tweetEntity)
                    senderEntities.find { it.id == tweetEntity.senderId }?.let {
                        tweet.sender = toSender(it)
                    }

                    tweet.images =
                        imageEntities.filter { it.tweetId == tweetEntity.id }.map { Image(it.url) }

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

                tweets
            }
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
        return appDatabase.senderDao().insert(senderEntity).blockingGet()
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