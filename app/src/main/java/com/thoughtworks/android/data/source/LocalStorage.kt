package com.thoughtworks.android.data.source

import com.thoughtworks.android.data.model.Tweet

interface LocalStorage {

    var isHintShown: Boolean

    fun getTweets(): List<Tweet>
}