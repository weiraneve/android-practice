package com.thoughtworks.android.data.source.feed

import com.thoughtworks.android.common.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val remote: FeedApiDataSource,
) {
    suspend fun getFeedList(): Flow<Result<FeedListEntity>> = remote.getFeedList()
}