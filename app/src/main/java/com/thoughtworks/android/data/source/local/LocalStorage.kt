package com.thoughtworks.android.data.source.local

import com.thoughtworks.android.data.model.Tweet
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface LocalStorage {
    var isHintShown: Boolean
    fun getTweetsFromRaw(): List<Tweet>
    fun updateTweets(tweets: List<Tweet>): Single<Boolean>
    fun getTweets(): Flowable<List<Tweet>>
}