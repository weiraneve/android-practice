package com.thoughtworks.android.di

import android.content.Context
import com.thoughtworks.android.data.api.RemoteFeedApiDataSource
import com.thoughtworks.android.data.api.RetrofitFeedApi
import com.thoughtworks.android.data.source.feed.FeedApiDataSource
import com.thoughtworks.android.network.client.ApiEndPoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideApiService(@ApplicationContext context: Context, json: Json): ApiEndPoints =
        ApiEndPoints(json)

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }
}