package com.thoughtworks.android.data.source

import android.content.Context
import com.thoughtworks.android.common.MyRepoResult
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.local.LocalStorage
import com.thoughtworks.android.data.source.local.LocalStorageImpl
import com.thoughtworks.android.data.source.remote.RemoteDataSourceImpl
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override suspend fun fetchTweets(): Flow<MyRepoResult<List<Tweet>>> {
        var tweets: List<Tweet>
        return flow {
            try {
                tweets = remoteDataSource.fetchTweets()
                emit(MyRepoResult.Success(tweets))
            } catch (e: Exception) {
                emit(MyRepoResult.Error(e))
            }

        }
    }

    override fun getTweets(): List<Tweet> {
        return localStorage.getTweets()
    }

    override suspend fun saveTweets(tweets: List<Tweet>) {
        localStorage.updateTweets(tweets)
    }

}