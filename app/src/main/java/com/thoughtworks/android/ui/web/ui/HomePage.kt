package com.thoughtworks.android.ui.web.ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun HomePage() {
    val firstRoute = "first_route"
    val webRoute = "web_route"
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
            WebScreen()
        }
    }
}