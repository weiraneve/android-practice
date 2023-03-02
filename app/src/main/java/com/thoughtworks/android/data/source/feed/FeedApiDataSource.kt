package com.thoughtworks.android.data.source.feed

import com.thoughtworks.android.common.bean.Result
import com.thoughtworks.android.data.source.feed.api.RetrofitFeedApi
import com.thoughtworks.android.data.source.feed.model.FeedListEntity
import com.thoughtworks.android.di.IoDispatcher
import com.thoughtworks.android.network.api.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface FeedApiDataSource {
    suspend fun getFeedList(): Flow<Result<FeedListEntity>>
}

class RemoteFeedApiDataSource @Inject constructor(
    private val feedApi: RetrofitFeedApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ApiService(), FeedApiDataSource {

    override suspend fun getFeedList() =
        withContext(ioDispatcher) { performRequest { feedApi.getFeedList() } }
}

