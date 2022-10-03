package com.thoughtworks.android.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import coil.compose.rememberAsyncImagePainter
import com.thoughtworks.android.data.model.Sender
import com.thoughtworks.android.data.model.Tweet

@Composable
fun TweetScreen(
    tweets: List<Tweet>,
    lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {}
                Lifecycle.Event.ON_START -> {}
                Lifecycle.Event.ON_STOP -> {}
                Lifecycle.Event.ON_DESTROY -> {}
                else -> {}
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        tweets.forEach {
            TweetItem(tweet = it)
        }
    }
}

@Composable
private fun TweetItem(tweet: Tweet) {
    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Avatar(tweet.sender)
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = tweet.sender?.nick.orEmpty(),
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            tweet.content.orEmpty().takeIf { it.isNotBlank() }?.let {
                Text(
                    modifier = Modifier
                        .background(Color.LightGray.copy(alpha = 0.3f))
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 4.dp, start = 0.dp, end = 4.dp),
                    text = it,
                    color = MaterialTheme.colors.secondaryVariant,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun Avatar(sender: Sender?) {
    sender?.avatar?.let {
        val painter = rememberAsyncImagePainter(it)
        Image(
            painter = painter,
            contentDescription = "null",
            Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}