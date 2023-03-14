package com.thoughtworks.android.ui.graphql.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.thoughtworks.android.network.apollo.apolloClient
import kotlinx.coroutines.launch

@Composable
fun GraphqlHome() {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    LaunchedEffect(email) {
        scope.launch {
            val response = try {
                apolloClient(context).mutation(LoginMutation(email)).execute()
            } catch (e: Exception) {
                null
            }
            content = response?.data?.login?.token.toString()
        }
    }
    Button(onClick = { email = "123456789@qq.com" }) {
        Text(text = content)
    }
}