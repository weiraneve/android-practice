package com.thoughtworks.android.data.source.local

import com.thoughtworks.android.data.model.Tweet
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface LocalStorage {
    var isKnown: Boolean
    fun getTweetsFromRaw(): List<Tweet>
    suspend fun updateTweets(tweets: List<Tweet>)
    fun getTweets(): Flowable<List<Tweet>>
}