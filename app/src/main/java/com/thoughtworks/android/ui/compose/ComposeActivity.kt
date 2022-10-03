package com.thoughtworks.android.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thoughtworks.android.common.Constants
import com.thoughtworks.android.data.model.Tweet


class ComposeActivity : AppCompatActivity() {

    private lateinit var tweets: List<Tweet>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        setContent {
            TweetScreen(tweets)
        }
    }

    private fun initData() {
        tweets = Constants.TWEETS
    }

    @Preview(showBackground = true)
    @Composable
    fun ContentPreview() {
        TweetScreen(tweets)
    }

}