package com.thoughtworks.android.data.source

import io.reactivex.rxjava3.core.Flowable
import com.thoughtworks.android.data.model.Tweet

interface DataSource {
    var isHintShown: Boolean
    fun fetchTweets()
    fun observeTweets(): Flowable<List<Tweet>>
}