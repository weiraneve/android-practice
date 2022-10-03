package com.thoughtworks.android.ui.compose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
            Content(tweet)
        }
    }
}

@Composable
private fun Content(tweet: Tweet) {
    tweet.content.orEmpty().takeIf { it.isNotBlank() }?.let { comment ->
        val showCommentEdit = remember { mutableStateOf(false) }
        Text(
            modifier = Modifier
                .background(Color.LightGray.copy(alpha = 0.3f))
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp, start = 0.dp, end = 4.dp)
                .clickable { showCommentEdit.value = true },
            text = comment,
            color = MaterialTheme.colors.secondaryVariant,
            fontSize = 14.sp
        )
        val commentValue = remember { mutableStateOf("") }
        if (showCommentEdit.value) {
            Comment(comment = commentValue.value, onSave = {
                showCommentEdit.value = false
                commentValue.value = it
            }, onCancel = {
                showCommentEdit.value = false
            })
        }
    }
}

@Composable
fun Comment(
    comment: String,
    onSave: (comment: String) -> Unit,
    onCancel: () -> Unit
) {
    val textValue = remember(comment) { mutableStateOf(comment) }
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = textValue.value,
            onValueChange = { textValue.value = it },
            modifier = Modifier
                .weight(1f, fill = false)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Button(onClick = { onSave.invoke(textValue.value) }) {
            Text(text = "save")
        }
        Spacer(modifier = Modifier.width(20.dp))
        Button(onClick = onCancel) { Text(text = "cancel") }
    }
}

@Composable
fun Avatar(sender: Sender?) {
    sender?.avatar?.let {
        var showDialog by remember { mutableStateOf(false) }
        val painter = rememberAsyncImagePainter(it)
        Image(
            painter = painter,
            contentDescription = "null",
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { showDialog = true },
            contentScale = ContentScale.Crop
        )
        if (showDialog) {
            BigAvatarDialog(it, onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun BigAvatarDialog(
    avatarUrl: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(20.dp)
                .background(Color.White)
        ) {
            val painter = rememberAsyncImagePainter(avatarUrl)
            Image(
                painter = painter,
                contentDescription = "avatar",
                modifier =
                Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }
    }

}