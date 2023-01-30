package com.thoughtworks.android.data.source.local

import com.thoughtworks.android.data.model.Tweet

interface LocalStorage {
    var isHintShown: Boolean
    fun getTweetsFromRaw(): List<Tweet>
    suspend fun updateTweets(tweets: List<Tweet>)
    suspend fun getTweets(): List<Tweet>
}