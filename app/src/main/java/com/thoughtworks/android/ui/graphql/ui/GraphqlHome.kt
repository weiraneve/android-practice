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
    var id by remember { mutableStateOf(1) }
    var subscriptionContent by remember { mutableStateOf("") }
    var queryContent by remember { mutableStateOf("") }

    val vehicleFlow =
        remember { apolloClient().subscription(VehicleSubscription(id.toString())).toFlow() }
    val vehicleResponse: ApolloResponse<VehicleSubscription.Data>? by vehicleFlow.collectAsState(
        initial = null
    )
    LaunchedEffect(id) {
        scope.launch {
            if (vehicleResponse == null) {
                subscriptionContent = NULL
                return@launch
            }
            subscriptionContent = when (vehicleResponse!!.data?.getVehicleUpdate?.type) {
                null -> ERROR
                else -> TRIP_BOOKED
            }
        }
    }

    LaunchedEffect(id) {
        scope.launch {
            queryContent = try {
                val response = apolloClient().query(VehicleEZQuery(id.toString())).execute()
                response.data?.vehicle?.type.toString()
            } catch (e: ApolloException) {
                e.printStackTrace()
                ERROR
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { id = 2 }) {
            Text(text = subscriptionContent)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = { id = 2 }) {
            Text(text = queryContent)
        }
    }
}

private const val ERROR = "error"
private const val NULL = "null"
private const val TRIP_BOOKED = "Trip booked! 🚀"