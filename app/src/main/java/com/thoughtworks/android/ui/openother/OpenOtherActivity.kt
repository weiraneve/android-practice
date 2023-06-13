package com.thoughtworks.android.ui.openother

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.thoughtworks.android.ui.openother.ui.OpenOtherHome
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpenOtherActivity : AppCompatActivity() {

    private val viewModel: OpenOtherViewModel by viewModels()
    private val requestCode = 1234 // 自定义的请求代码

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenOtherHome()
        }

        viewModel.permissionRequestEvent.observe(this) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.QUERY_ALL_PACKAGES),
                requestCode
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == this.requestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.openOtherApp()
            } else {
                // Permission denied.
            }
        }
    }
}

