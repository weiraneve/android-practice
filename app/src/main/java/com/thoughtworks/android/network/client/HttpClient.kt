package com.thoughtworks.android.network.client

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

open class HttpClient {

    var okHttpClient: OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
        readTimeout(TIME_OUT, TimeUnit.SECONDS)
        writeTimeout(TIME_OUT, TimeUnit.SECONDS)
    }.build()

    companion object {
        const val TIME_OUT = 5L
        const val CONNECT_TIME_OUT = 15L
    }
}
