package com.thoughtworks.android.data.source.feed

import javax.inject.Inject

class FeedRepository @Inject constructor(private val feedApiDataSource: FeedApiDataSource) {
    suspend fun getFeedList() = feedApiDataSource.getFeedList()
}