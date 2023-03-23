package com.thoughtworks.android.ui.graphql.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.thoughtworks.android.network.apollo.apolloClient
import kotlinx.coroutines.launch

@Composable
fun GraphqlHome() {

    val scope = rememberCoroutineScope()
    var subId by remember { mutableStateOf(1) }
    var queryContent by remember { mutableStateOf("") }
    var mutationContent by remember { mutableStateOf("") }
    var subscriptionContent by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        scope.launch {
            queryContent = try {
                val response = apolloClient().query(HeroGetQuery()).execute()
                response.data?.getHeroesNotIsPick?.name.toString()
            } catch (e: ApolloException) {
                e.printStackTrace()
                ERROR
            }
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            mutationContent = try {
                val response = apolloClient().mutation(HeroClearMutation()).execute()
                response.data?.clearAllHero.toString()
            } catch (e: ApolloException) {
                e.printStackTrace()
                ERROR
            }
        }
    }

    val vehicleFlow =
        remember { apolloClient().subscription(HeroUpdateSubscription(subId.toString())).toFlow() }
    val vehicleResponse: ApolloResponse<HeroUpdateSubscription.Data>? by vehicleFlow.collectAsState(
        initial = null
    )
    LaunchedEffect(subId) {
        scope.launch {
            if (vehicleResponse == null) {
                subscriptionContent = NULL
                return@launch
            }
            subscriptionContent = when (vehicleResponse!!.data?.getHeroUpdate?.name) {
                null -> ERROR
                else -> TRIP_BOOKED
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {}) {
            Text(text = queryContent)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {}) {
            Text(text = mutationContent)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = { subId = 2 }) {
            Text(text = subscriptionContent)
        }

    }
}

private const val ERROR = "error"
private const val NULL = "null"
private const val TRIP_BOOKED = "Trip booked! 🚀"