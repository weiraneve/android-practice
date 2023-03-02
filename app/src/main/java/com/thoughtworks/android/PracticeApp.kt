package com.thoughtworks.android

import android.app.Application
import com.thoughtworks.android.common.bus.FlowBusInitializer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PracticeApp : Application() {

//    private val flowBusInitializer = FlowBusInitializer

    // 避免内存泄露
//    override fun onCreate() {
//        super.onCreate()
//        flowBusInitializer.init(this)
//    }
}
