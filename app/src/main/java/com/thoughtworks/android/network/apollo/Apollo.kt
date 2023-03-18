package com.thoughtworks.android.network.apollo

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient

private var instance: ApolloClient? = null

fun apolloClient(): ApolloClient {
    instance?.run {
        return this
    }
    val okHttpClient = OkHttpClient.Builder()
        .build()

    instance = ApolloClient.Builder()
        .serverUrl(URL)
        .webSocketServerUrl(WS)
        .okHttpClient(okHttpClient)
        .webSocketReopenWhen { throwable, attempt ->
            Log.e("Apollo", "WebSocket got disconnected, reopening after a delay", throwable)
            delay(attempt * 1000)
            true
        }
        .build()

    return instance!!

}

//private const val URL = "https://apollo-fullstack-tutorial.herokuapp.com/graphql"
private const val URL = "http://steveay.com:8085/graphql"
// need upgrade client websocket
private const val WS = "ws://steveay.com:8085/graphql"