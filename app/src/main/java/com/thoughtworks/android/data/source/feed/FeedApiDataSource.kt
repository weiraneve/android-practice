package com.thoughtworks.android.data.source.feed

import com.thoughtworks.android.common.Result
import kotlinx.coroutines.flow.Flow

interface FeedApiDataSource {
    suspend fun getFeedList(): Flow<Result<FeedListEntity>>
}

