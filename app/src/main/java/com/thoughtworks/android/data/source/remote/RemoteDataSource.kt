package com.thoughtworks.android.data.source.remote

import com.thoughtworks.android.data.model.Tweet

interface RemoteDataSource {
    suspend fun fetchTweets(): List<Tweet>
}