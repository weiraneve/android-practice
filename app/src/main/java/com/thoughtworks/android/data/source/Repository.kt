package com.thoughtworks.android.data.source

import android.content.Context
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.local.LocalStorage
import com.thoughtworks.android.data.source.local.LocalStorageImpl
import com.thoughtworks.android.data.source.remote.RemoteDataSourceImpl
import io.reactivex.rxjava3.core.Flowable

class Repository(context: Context?) : DataSource {

    private val localStorage: LocalStorage
    private val remoteDataSource = RemoteDataSourceImpl()

    init {
        localStorage = LocalStorageImpl(context!!)
    }

    override var isKnown: Boolean
        get() = localStorage.isKnown
        set(isKnown) {
            localStorage.isKnown = isKnown
        }

    override suspend fun fetchTweets() {
        val tweets = remoteDataSource.fetchTweets()
        val filteredTweets: MutableList<Tweet> = mutableListOf()
        for (tweet in tweets) {
            if (tweet.error != null || tweet.unknownError != null) {
                continue
            }
            filteredTweets.add(tweet)
        }

        localStorage.updateTweets(filteredTweets)
    }

    override fun observeTweets(): Flowable<List<Tweet>> {
        return localStorage.getTweets()
    }

}