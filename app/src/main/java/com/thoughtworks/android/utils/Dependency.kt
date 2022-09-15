package com.thoughtworks.android.utils

import android.annotation.SuppressLint
import android.content.Context
import com.thoughtworks.android.data.source.DataSource
import com.thoughtworks.android.data.source.Repository
import com.thoughtworks.android.utils.schedulers.AndroidSchedulerProvider
import com.thoughtworks.android.utils.schedulers.SchedulerProvider

class Dependency private constructor(context: Context) {

    val dataSource: DataSource
    val schedulerProvider: SchedulerProvider

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

    init {
        dataSource = Repository(context)
        schedulerProvider = AndroidSchedulerProvider()
    }
}