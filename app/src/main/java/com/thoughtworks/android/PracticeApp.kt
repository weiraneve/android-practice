package com.thoughtworks.android

import android.app.Activity
import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlin.system.exitProcess


@HiltAndroidApp
class PracticeApp : Application() {

    private val activities: MutableList<Activity> = mutableListOf()

    companion object {
        @Volatile
        private var INSTANCE: PracticeApp? = null

        private fun instance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: PracticeApp().also { INSTANCE = it }
        }

        fun addActivity(activity: Activity) =
            instance().addActivity(activity)

        fun exit() = instance().exit()
    }

    // add Activity
    private fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    // 关闭每一个list内的activity
    private fun exit() {
        try {
            activities.forEach { it.finish() }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            exitProcess(0)
        }
    }

    // 杀进程
    override fun onLowMemory() {
        super.onLowMemory()
        System.gc()
    }
}
