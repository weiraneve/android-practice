package com.thoughtworks.android.ui.recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thoughtworks.android.R
import com.thoughtworks.android.viewmodel.TweetsViewModel
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
        with(tweetsViewModel) {
            lifecycleScope.launchWhenCreated {
                tweetList.collect {
                    tweetAdapter.setData(it)
                    swipeRefreshLayout.isRefreshing = false
                }
            }
            lifecycleScope.launchWhenCreated {
                isNeedRefresh.collect {
                    swipeRefreshLayout.isRefreshing = it
                }
            }
            lifecycleScope.launchWhenCreated {
                errorMsg.collect {
                    it?.let { showError(it) }
                }
            }
        }

        tweetsViewModel.fetchTweets()
    }

    private fun initUI() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        tweetAdapter = TweetAdapter(this)
        recyclerView.adapter = tweetAdapter

        swipeRefreshLayout.setOnRefreshListener {
            tweetsViewModel.refreshTweets()
        }
    }

    private fun showError(t: Throwable) {
        Toast.makeText(
            this@TweetsActivity,
            t.message,
            Toast.LENGTH_SHORT
        ).show()
    }

}