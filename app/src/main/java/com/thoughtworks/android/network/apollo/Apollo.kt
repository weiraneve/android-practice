package com.thoughtworks.android.network.apollo

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

private var instance: ApolloClient? = null

fun apolloClient(context: Context): ApolloClient {
    instance?.run {
        return this
    }
    val okHttpClient = OkHttpClient.Builder()
        .build()

    instance = ApolloClient.Builder()
        .serverUrl(URL)
        .okHttpClient(okHttpClient)
        .build()

    return instance!!

}

private const val URL = "https://apollo-fullstack-tutorial.herokuapp.com/graphql"