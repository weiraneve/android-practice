package com.thoughtworks.android.ui.web.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.thoughtworks.android.R

@Composable
fun WebScreen(url: String) {
    val state = rememberWebViewState(url)
    Column(
        modifier = Modifier
            .background(colorResource(R.color.white))
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        WebView(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            captureBackPresses = false
        )
    }
}