package com.thoughtworks.android.data.source

import com.thoughtworks.android.data.model.Tweet

interface DataSource {
    var isHintShown: Boolean
    suspend fun fetchTweets()
    fun observeTweets(): List<Tweet>
}