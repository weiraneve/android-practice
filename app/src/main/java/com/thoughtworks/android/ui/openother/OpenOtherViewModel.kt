package com.thoughtworks.android.ui.openother

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class OpenOtherViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _permissionRequestEvent = MutableLiveData<Unit>()
    val permissionRequestEvent: LiveData<Unit> = _permissionRequestEvent

    fun openOtherApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.QUERY_ALL_PACKAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                _permissionRequestEvent.value = Unit
            } else {
                launchApp()
            }
        } else {
            launchApp()
        }
    }

    private fun launchApp() {
        val packageName = "com.weiran.mynowinandroid"
        val packageManager = context.packageManager
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(launchIntent)
        } else {
            Log.i(TAG, "App is not installed: $packageName")
        }
    }

    companion object {
        private const val TAG = "OpenOtherViewModel"
    }
}
