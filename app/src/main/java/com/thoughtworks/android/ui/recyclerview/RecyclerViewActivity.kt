package com.thoughtworks.android.ui.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.android.R
import com.thoughtworks.android.model.Tweet

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var  tweetAdapter : TweetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view_layout)
        initUI()
    }

    private fun initUI() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        tweetAdapter = TweetAdapter()
        recyclerView.adapter = tweetAdapter
        tweetAdapter.setTweets(createTweets())
    }

    private fun createTweets(): List<Tweet> {
        val tweets = ArrayList<Tweet>()
        tweets.add(Tweet("john", "沙发！"))
        tweets.add(Tweet("Sum", "二楼！"))
        tweets.add(Tweet("Tom"))
        tweets.add(Tweet("Allen"))
        tweets.add(Tweet("Jim"))
        tweets.add(Tweet("Steve", "stay hungry, stay foolish."))
        tweets.add(Tweet("kobe"))
        tweets.add(Tweet("doubleLift"))
        tweets.add(Tweet("aoLi"))
        tweets.add(Tweet("jobs"))
        tweets.add(Tweet("bryant"))
        tweets.add(Tweet("yiLiang"))
        tweets.add(Tweet("puD"))
        tweets.add(Tweet("jojo"))

        return tweets
    }

}