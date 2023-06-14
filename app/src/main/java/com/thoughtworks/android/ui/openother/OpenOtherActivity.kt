package com.thoughtworks.android.ui.openother

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.thoughtworks.android.ui.openother.ui.OpenOtherHome
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpenOtherActivity : AppCompatActivity() {

    private val viewModel: OpenOtherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenOtherHome()
        }

        viewModel.permissionRequestEvent.observe(this) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.QUERY_ALL_PACKAGES),
                    REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.openOtherApp()
            } else {
                // Permission denied.
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 0 // 自定义的请求代码
    }
}

