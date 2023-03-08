package com.thoughtworks.android.ui.navigation.composenav.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ForYouScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("ForYou")
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            (0..20).forEach {
                item(it) {
                    Spacer(modifier = Modifier.height(100.dp))
                    Text(text = it.toString())
                }
            }
        }
    }
}