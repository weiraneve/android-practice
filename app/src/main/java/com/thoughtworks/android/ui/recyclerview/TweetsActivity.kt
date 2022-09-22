package com.thoughtworks.android.ui.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.android.R
import com.thoughtworks.android.common.Definitions
import com.thoughtworks.android.model.Tweet

class TweetsActivity : AppCompatActivity() {

    private val gson: Gson = Gson()
    private lateinit var tweetAdapter: TweetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tweets_layout)
        initUI()
        initData()
    }

    private fun initUI() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        tweetAdapter = TweetAdapter(this)
        recyclerView.adapter = tweetAdapter

        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            tweetAdapter.setData(tweets.shuffled())
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initData() {
        tweetAdapter.setData(tweets)
    }

    private val tweets: List<Tweet>
        get() {
            val tweets: List<Tweet> =
                gson.fromJson(Definitions.TWEET, object : TypeToken<List<Tweet>>() {}.type)
            val filteredTweets: List<Tweet> = tweets.filter {
                it.error == null && it.unknownError == null
            }
            return filteredTweets
        }

}