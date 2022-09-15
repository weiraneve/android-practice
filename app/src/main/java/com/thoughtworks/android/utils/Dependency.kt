package com.thoughtworks.android.utils

import android.annotation.SuppressLint
import android.content.Context
import com.thoughtworks.android.data.source.DataSource
import com.thoughtworks.android.data.source.Repository

class Dependency private constructor(context: Context) {

    val dataSource: DataSource

    init {
        dataSource = Repository(context)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: Dependency? = null

        fun getInstance(context: Context): Dependency? {
            if (instance == null) {
                synchronized(Dependency::class.java) {
                    if (instance == null) {
                        instance = Dependency(context)
                    }
                }
            }
            return instance
        }
    }

}