package com.thoughtworks.android.data.source

import com.thoughtworks.android.data.model.Tweet

interface LocalStorage {

    var isKnown: Boolean

    fun getTweets(): List<Tweet>
}