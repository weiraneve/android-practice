package com.thoughtworks.android.ui.navigation.composenav

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.thoughtworks.android.ui.navigation.composenav.navigation.MyNavigationBar
import com.thoughtworks.android.ui.navigation.composenav.navigation.TopLevelDestination
import com.thoughtworks.android.ui.navigation.composenav.navigation.forYouScreen
import com.thoughtworks.android.ui.navigation.composenav.navigation.fouYouRoute
import com.thoughtworks.android.ui.navigation.composenav.navigation.interestScreen
import com.thoughtworks.android.ui.navigation.composenav.navigation.navigateToTopLevelDestination
import com.thoughtworks.android.ui.navigation.composenav.navigation.savedAnyScreen
import com.thoughtworks.android.ui.navigation.composenav.navigation.savedScreen

@OptIn(
    ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class
)
@Composable
fun ComposeNavHome() {
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()
    val navController = rememberAnimatedNavController()
    val currentDestination: NavDestination? =
        navController.currentBackStackEntryAsState().value?.destination

    Scaffold(bottomBar = {
        MyNavigationBar(
            destinations = topLevelDestinations,
            navController = navController,
            onNavigateToDestination = { destinations, NavController ->
                navigateToTopLevelDestination(destinations, NavController)
            },
            currentDestination = currentDestination
        )

    }) { padding ->

        AnimatedNavHost(
            navController = navController,
            startDestination = fouYouRoute,
            modifier = Modifier
                .padding(padding)
                .consumedWindowInsets(padding),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(350)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(350)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(350)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(350)
                )
            }
        ) {
            forYouScreen()
            savedScreen(navController)
            savedAnyScreen()
            interestScreen()
        }
    }
}