package com.thoughtworks.android.data.api

import com.thoughtworks.android.common.Result
import com.thoughtworks.android.data.source.feed.FeedApiDataSource
import com.thoughtworks.android.data.source.feed.FeedListEntity
import com.thoughtworks.android.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

interface RetrofitFeedApi {

    @GET("/friend/json")
    suspend fun getFeedList(): Response<FeedListEntity>

    companion object {
        const val baseUrl = "https://www.wanandroid.com"
    }
}

class RemoteFeedApiDataSource @Inject constructor(
    private val feedApi: RetrofitFeedApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ApiService(), FeedApiDataSource {

    override suspend fun getFeedList(): Flow<Result<FeedListEntity>> = withContext(ioDispatcher) {
        performRequest { feedApi.getFeedList() }
    }
}