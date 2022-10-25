package com.thoughtworks.android.data.source

import com.thoughtworks.android.data.model.Tweet
import kotlinx.coroutines.flow.Flow
import com.thoughtworks.android.common.MyRepoResult

interface Repository {
    var isHintShown: Boolean
    suspend fun fetchTweets(): Flow<MyRepoResult<List<Tweet>>>
    suspend fun getTweets(): List<Tweet>
    suspend fun saveTweets(tweets: List<Tweet>)
}