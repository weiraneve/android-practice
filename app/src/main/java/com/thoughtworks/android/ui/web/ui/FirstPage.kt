package com.thoughtworks.android.ui.web.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FirstPage() {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(300.dp))
        Text(text = "FirstPage")
    }
}