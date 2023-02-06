package com.thoughtworks.android.di

import com.thoughtworks.android.data.source.feed.api.RetrofitFeedApi
import com.thoughtworks.android.data.source.feed.FeedApiDataSource
import com.thoughtworks.android.data.source.feed.RemoteFeedApiDataSource
import com.thoughtworks.android.network.client.ApiEndPoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providerFeedApi(apiEndPoints: ApiEndPoints): RetrofitFeedApi =
        apiEndPoints.createService(RetrofitFeedApi::class.java, RetrofitFeedApi.baseUrl)

    @Provides
    @Singleton
    fun providerFeedApiDataSource(
        feedApi: RetrofitFeedApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): FeedApiDataSource = RemoteFeedApiDataSource(feedApi, ioDispatcher)

    @Provides
    @Singleton
    fun provideApiService(json: Json): ApiEndPoints =
        ApiEndPoints(json)

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }
}