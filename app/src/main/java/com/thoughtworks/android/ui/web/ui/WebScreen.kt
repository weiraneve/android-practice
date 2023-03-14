package com.thoughtworks.android.ui.web.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewNavigator
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.thoughtworks.android.R

@Composable
fun WebScreen(url: String) {
    val state = rememberWebViewState(url)
    val context = LocalContext.current
    val navigator = rememberWebViewNavigator()
    var webView by remember { mutableStateOf<WebView?>(null) }
    var progress by remember { mutableStateOf(0f) }
    Column(
        modifier = Modifier
            .background(colorResource(R.color.white))
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        val chromeClient = object : AccompanistWebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progress = (newProgress / 100f).coerceIn(0f, 1f)
            }
        }
        Column(
            modifier = Modifier
                .background(colorResource(R.color.white))
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            WebView(
                state = state,
                navigator = navigator,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                chromeClient = chromeClient,
                factory = {
                    webView = WebView(it)
                    webView!!
                },
                captureBackPresses = false
            )
            ProgressIndicator(progress)
            WebBottomBar(webView, url, context, navigator, state)
        }
    }
}

@Composable
private fun ProgressIndicator(progress: Float) {
    AnimatedVisibility(visible = (progress > 0f && progress < 1f)) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(R.color.blue),
            backgroundColor = colorResource(R.color.white)
        )
    }
}

@Composable
private fun WebBottomBar(
    webView: WebView?,
    url: String,
    context: Context,
    navigator: WebViewNavigator,
    state: WebViewState
) {
    Row(
        modifier = Modifier
            .background(colorResource(R.color.white))
            .height(50.dp)
    ) {
        Button(
            onClick = {
                webView?.let {
                    if (!goBack(it, url)) {
                        if (context is AppCompatActivity) {
                            context.onBackPressedDispatcher.onBackPressed()
                        }
                    }
                }
            },
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp),
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(R.color.white),
            ),
            contentPadding = PaddingValues(17.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_web_back),
                contentDescription = null
            )
        }
        Button(
            onClick = {
                if (navigator.canGoForward) {
                    navigator.navigateForward()
                }
            },
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp),
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(R.color.white),
            ),
            contentPadding = PaddingValues(17.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_web_forward),
                contentDescription = null
            )
        }
        Button(
            onClick = {
                navigator.reload()
            },
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp),
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(R.color.white),
            ),
            contentPadding = PaddingValues(17.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_web_refresh),
                contentDescription = null
            )
        }
        Button(
            onClick = {
                try {
                    if (context is AppCompatActivity) {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(state.content.getCurrentUrl())
                        )
                        intent.addCategory(Intent.CATEGORY_BROWSABLE)
                        context.startActivity(intent)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp),
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(R.color.white),
            ),
            contentPadding = PaddingValues(17.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_web_browse),
                contentDescription = null
            )
        }
    }
}

fun goBack(webView: WebView, originalUrl: String): Boolean {
    val canBack = webView.canGoBack()
    if (canBack) webView.goBack()
    val backForwardList = webView.copyBackForwardList()
    val currentIndex = backForwardList.currentIndex
    if (currentIndex == 0) {
        val currentUrl = backForwardList.currentItem?.url
        val currentHost = Uri.parse(currentUrl).host
        // 栈底不是链接则直接返回
        if (currentHost.isNullOrBlank()) return false
        // 栈底链接不是原始链接则直接返回
        if (originalUrl != currentUrl) return false
    }
    return canBack
}