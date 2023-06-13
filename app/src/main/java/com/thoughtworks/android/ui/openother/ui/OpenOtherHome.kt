package com.thoughtworks.android.ui.openother.ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.android.ui.openother.OpenOtherViewModel

@Composable
fun OpenOtherHome() {
    val viewModel: OpenOtherViewModel = viewModel()
    Button(onClick = { viewModel.openOtherApp() }) {
        Text(text = "打开另一个应用")
    }
}