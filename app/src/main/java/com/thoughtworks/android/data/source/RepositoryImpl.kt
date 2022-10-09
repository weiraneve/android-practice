package com.thoughtworks.android.data.source

import android.content.Context
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.local.LocalStorage
import com.thoughtworks.android.data.source.local.LocalStorageImpl
import com.thoughtworks.android.data.source.remote.RemoteDataSourceImpl
import com.thoughtworks.android.exception.CustomException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(@ApplicationContext context: Context) : Repository {

    private val localStorage: LocalStorage
    private val remoteDataSource = RemoteDataSourceImpl()

    init {
        localStorage = LocalStorageImpl(context)
    }

    override var isHintShown: Boolean
        get() = localStorage.isHintShown
        set(isKnown) {
            localStorage.isHintShown = isKnown
        }

    override suspend fun fetchTweets(): List<Tweet> {
        var tweets: List<Tweet>
        try {
            tweets = remoteDataSource.fetchTweets()
            tweets = tweets.filter { it.error == null && it.unknownError == null }
            localStorage.updateTweets(tweets)
        } catch (e: Exception) {
            tweets = getTweets()
            if (tweets.isEmpty()) {
                throw CustomException("NetWork error and database can't get tweets!")
            }
        }
        return tweets
    }

    private fun getTweets(): List<Tweet> {
        return localStorage.getTweets()
    }

}