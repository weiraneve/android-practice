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
import com.thoughtworks.android.common.Result
import com.thoughtworks.android.ui.compose.TweetsViewModel
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
        lifecycleScope.launchWhenCreated {
            tweetsViewModel.uiState.collect { res ->
                when (res) {
                    is Result.Loading -> swipeRefreshLayout.isRefreshing = true
                    is Result.Success -> {
                        tweetAdapter.setData(res.data ?: listOf())
                        swipeRefreshLayout.isRefreshing = false
                    }
                    is Result.Error -> {
                        res.exception.message?.let { showError(it) }
                        swipeRefreshLayout.isRefreshing = false
                    }
                }
            }
        }
        tweetsViewModel.observeTweets()
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

    private fun showError(errorMsg: String) {
        Toast.makeText(
            this@TweetsActivity,
            errorMsg,
            Toast.LENGTH_SHORT
        ).show()
    }

}