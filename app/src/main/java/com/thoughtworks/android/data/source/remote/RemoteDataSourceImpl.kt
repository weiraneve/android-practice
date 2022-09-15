package com.thoughtworks.android.data.source.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.android.data.model.Tweet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request


class RemoteDataSourceImpl : RemoteDataSource {

    companion object {
        private const val TWEETS_URL =
            "https://thoughtworks-mobile-2018.herokuapp.com/user/jsmith/tweets"
    }

    private val client = OkHttpClient()
    private val gson = Gson()

    override suspend fun fetchTweets(): List<Tweet> {
        return withContext(Dispatchers.IO) {
            val request: Request = Request.Builder()
                .url(TWEETS_URL)
                .build()

            val response = client.newCall(request).execute()

            return@withContext gson.fromJson<List<Tweet>>(
                response.body!!.string(),
                object : TypeToken<List<Tweet>>() {}.type
            )
        }
    }

}