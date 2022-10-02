package com.thoughtworks.android.ui.recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thoughtworks.android.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TweetsActivity : AppCompatActivity() {

    private lateinit var tweetsViewModel: TweetsViewModel
    private val swipeRefreshLayout: SwipeRefreshLayout by lazy { findViewById(R.id.swipeRefreshLayout) }
    private lateinit var tweetAdapter: TweetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tweets_layout)
        initViewModel()
        initUI()
    }

    private fun initViewModel() {
        tweetsViewModel = ViewModelProvider(this)[TweetsViewModel::class.java]
        tweetsViewModel.tweetList.observe(this) { tweets ->
            tweetAdapter.setData(if (tweetsViewModel.isNeedRefresh) tweets.shuffled() else tweets)
            swipeRefreshLayout.isRefreshing = false
        }

        tweetsViewModel.fetchTweets {
            showError(it)
        }
    }

    private fun initUI() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        tweetAdapter = TweetAdapter(this)
        recyclerView.adapter = tweetAdapter

        swipeRefreshLayout.setOnRefreshListener {
            tweetsViewModel.fetchTweets {
                showError(it)
            }
            tweetsViewModel.refresh()
        }
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(
            this@TweetsActivity,
            throwable.message,
            Toast.LENGTH_SHORT
        ).show()
        swipeRefreshLayout.isRefreshing = false
    }

}