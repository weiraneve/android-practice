package com.thoughtworks.android.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.thoughtworks.android.common.Constants
import com.thoughtworks.android.data.model.Sender
import com.thoughtworks.android.data.model.Tweet


class ComposeActivity : AppCompatActivity() {

    private lateinit var tweets: List<Tweet>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        setContent {
            Content()
        }
    }

    private fun initData() {
        tweets = Constants.TWEETS
    }

    @Composable
    private fun Content() {
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

    @Preview(showBackground = true)
    @Composable
    fun ContentPreview() {
        Content()
    }

}