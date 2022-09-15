package com.thoughtworks.android.data.source

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.android.R
import com.thoughtworks.android.common.Constants
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.utils.FileUtil
import com.thoughtworks.android.utils.SharedPreferenceUtil

class LocalStorageImpl(private val context: Context): LocalStorage {

    private val gson = Gson()

    companion object {
        private const val KEY_KNOWN = "known"
    }

    override var isKnown: Boolean
        get() = SharedPreferenceUtil.readBoolean(context, Constants.SHARED_PREFERENCE_FILE, KEY_KNOWN, false)
        set(value) {
            SharedPreferenceUtil.writeBoolean(context, Constants.SHARED_PREFERENCE_FILE, KEY_KNOWN, value)
        }

    override fun getTweets(): List<Tweet> {
        val tweets: List<Tweet> = gson.fromJson(
            FileUtil.getStringFromRaw(context, R.raw.tweets),
            object : TypeToken<List<Tweet>>() {}.type
        )

        val filteredTweets: MutableList<Tweet> = mutableListOf()
        for (tweet in tweets) {
            if (tweet.error != null || tweet.unknownError != null) {
                continue
            }
            filteredTweets.add(tweet)
        }
        return filteredTweets
    }

}