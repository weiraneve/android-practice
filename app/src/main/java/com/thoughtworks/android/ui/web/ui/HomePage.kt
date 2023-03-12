package com.thoughtworks.android.ui.web.ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun HomePage() {
    val navController = rememberNavController()
    Button(onClick = { navController.navigate(webRoute) }) { Text(text = "Enter WebView") }
    NavHost(
        navController = navController,
        startDestination = firstRoute
    ) {
        composable(route = firstRoute) {
            FirstPage()
        }

        composable(route = webRoute) {
            WebScreen(demoUrl)
        }
    }
}

const val firstRoute = "first_route"
const val webRoute = "web_route"
const val demoUrl = "https://www.zhihu.com/question/378005304/answer/2927052720"