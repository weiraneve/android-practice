package com.thoughtworks.android.data.source

import android.content.Context
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.local.LocalStorage
import com.thoughtworks.android.data.source.local.LocalStorageImpl
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers

class Repository(context: Context?) : DataSource {

    private val localStorage: LocalStorage

    init {
        localStorage = LocalStorageImpl(context!!)
    }

    override var isKnown: Boolean
        get() = localStorage.isKnown
        set(isKnown) {
            localStorage.isKnown = isKnown
        }

    override fun fetchTweets() {
        localStorage.updateTweets(localStorage.getTweetsFromRaw())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    override fun observeTweets(): Flowable<List<Tweet>> {
        return localStorage.getTweets()
    }

}