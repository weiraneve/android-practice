package com.thoughtworks.android.data.source

import com.thoughtworks.android.data.model.Tweet

interface Repository {
    var isHintShown: Boolean
    suspend fun fetchTweets(): List<Tweet>
}