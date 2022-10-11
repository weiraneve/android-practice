package com.thoughtworks.android.data.source

import com.thoughtworks.android.data.model.Tweet
import kotlinx.coroutines.flow.Flow
import com.thoughtworks.android.common.MyResult

interface Repository {
    var isHintShown: Boolean
    suspend fun fetchTweets(): Flow<MyResult<List<Tweet>>>
}