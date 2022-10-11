package com.thoughtworks.android.data.source

import android.content.Context
import com.thoughtworks.android.common.MyResult
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.local.LocalStorage
import com.thoughtworks.android.data.source.local.LocalStorageImpl
import com.thoughtworks.android.data.source.remote.RemoteDataSourceImpl
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
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

    override suspend fun fetchTweets(): Flow<MyResult<List<Tweet>>> {
        var tweets: List<Tweet>
        return flow {
            emit(MyResult.Loading)
            emit(
                try {
                    tweets = remoteDataSource.fetchTweets()
                    tweets = tweets.filter { it.error == null && it.unknownError == null }
                    localStorage.updateTweets(tweets)
                    MyResult.Success(tweets)
                } catch (e: UnknownHostException) {
                    tweets = getTweets()
                    if (tweets.isEmpty()) {
                        MyResult.Error(e)
                    } else MyResult.Success(tweets)
                } catch (e: Throwable) {
                    MyResult.Error(e)
                }
            )
        }
    }

    private fun getTweets(): List<Tweet> {
        return localStorage.getTweets()
    }

}