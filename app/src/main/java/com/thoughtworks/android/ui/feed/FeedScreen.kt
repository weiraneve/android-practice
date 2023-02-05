package com.thoughtworks.android.ui.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FeedScreen(viewModel: FeedViewModel = viewModel()) {
    val uiState by viewModel.feedState.collectAsState()
    FeedScreenContent(
        uiState = uiState,
        dispatchAction = viewModel::dispatchAction
    )
}

@Composable
fun FeedScreenContent(
    uiState: FeedUiState,
    dispatchAction: (FeedUiAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Button(onClick = { dispatchAction(FeedUiAction.FeedListAction) }) { Text(text = "GetFeedList") }
        Text(
            text = uiState.dataText ?: "has null data",
            modifier = Modifier.padding(16.dp)
        )
    }
}