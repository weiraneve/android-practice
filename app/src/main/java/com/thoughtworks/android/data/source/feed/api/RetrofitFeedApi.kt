package com.thoughtworks.android.data.source.feed.api

import com.thoughtworks.android.data.source.feed.model.FeedListEntity
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitFeedApi {

    @GET("/friend/json")
    suspend fun getFeedList(): Response<FeedListEntity>

    companion object {
        const val baseUrl = "https://www.wanandroid.com"
    }
}