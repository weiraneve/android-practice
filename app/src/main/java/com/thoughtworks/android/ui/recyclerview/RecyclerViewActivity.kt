package com.thoughtworks.android.ui.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.android.PracticeApp
import com.thoughtworks.android.R
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.utils.Dependency

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var tweetAdapter: TweetAdapter
    private lateinit var dependency: Dependency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view_layout)
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
            tweetAdapter.setData(dependency.getLocalStorage().getTweets().shuffled())
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initData() {
        dependency = (application as PracticeApp).getDependency()
        tweetAdapter.setData(dependency.getLocalStorage().getTweets())
    }

}