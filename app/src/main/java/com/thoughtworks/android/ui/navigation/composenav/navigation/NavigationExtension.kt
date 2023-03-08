package com.thoughtworks.android.ui.navigation.composenav.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.composable
import com.thoughtworks.android.ui.navigation.composenav.page.ForYouScreen
import com.thoughtworks.android.ui.navigation.composenav.page.InterestScreen
import com.thoughtworks.android.ui.navigation.composenav.page.SavedAnyScreen
import com.thoughtworks.android.ui.navigation.composenav.page.SavedScreen


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.forYouScreen() =
    composable(route = fouYouRoute) { ForYouScreen() }

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.savedScreen(navController: NavController) =
    composable(route = savedRoute) { SavedScreen(navController) }

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.interestScreen() =
    composable(route = interestRoute) { InterestScreen() }

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.savedAnyScreen() =
    composable(route = savedAnyRoute) { SavedAnyScreen() }

fun navigateToTopLevelDestination(
    topLevelDestination: TopLevelDestination,
    navController: NavController
) {
    val topLevelNavOptions = navOptions {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }

    when (topLevelDestination) {
        TopLevelDestination.FOR_YOU -> navController.navigate(
            fouYouRoute,
            topLevelNavOptions
        )

        TopLevelDestination.SAVED -> navController.navigate(
            savedRoute,
            topLevelNavOptions
        )

        TopLevelDestination.INTEREST -> navController.navigate(
            interestRoute,
            topLevelNavOptions
        )
    }
}

const val fouYouRoute = "for_you_route"
const val savedRoute = "saved_route"
const val savedAnyRoute = "saved_route/any"
const val interestRoute = "interest_route"