package com.thoughtworks.android.utils

import android.annotation.SuppressLint
import android.content.Context
import com.thoughtworks.android.data.source.LocalStorage
import com.thoughtworks.android.data.source.LocalStorageImpl

class Dependency private constructor(context: Context) {

    private val localStorage: LocalStorage

    init {
        localStorage = LocalStorageImpl(context)
    }

    fun getLocalStorage(): LocalStorage {
        return localStorage
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