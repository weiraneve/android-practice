package com.thoughtworks.android.data.source

import android.content.Context
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.local.LocalStorage
import com.thoughtworks.android.data.source.local.LocalStorageImpl
import com.thoughtworks.android.data.source.remote.RemoteDataSourceImpl
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class Repository @Inject constructor(@ApplicationContext context: Context) : DataSource {

    private val localStorage: LocalStorage
    private val remoteDataSource = RemoteDataSourceImpl()

    override var isHintShown: Boolean
        get() = localStorage.isHintShown
        set(isKnown) {
            localStorage.isHintShown = isKnown
        }

    override suspend fun fetchTweets() {
        val tweets = remoteDataSource.fetchTweets()
        val filteredTweets: MutableList<Tweet> = mutableListOf()
        tweets.filterNot { it.error != null || it.unknownError != null }.forEach {filteredTweets.add(it)}
        localStorage.updateTweets(filteredTweets)
    }

    override fun observeTweets(): Flowable<List<Tweet>> {
        return localStorage.getTweets()
    }

    init {
        localStorage = LocalStorageImpl(context)
    }
}