package com.thoughtworks.android

import android.app.Application
import com.thoughtworks.android.utils.Dependency

class PracticeApp: Application() {

    private lateinit var dependency: Dependency

    override fun onCreate() {
        super.onCreate()
        dependency = Dependency.getInstance(this)!!
    }

    fun getDependency(): Dependency {
        return dependency
    }

}