package com.thoughtworks.android.ui.recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thoughtworks.android.PracticeApp
import com.thoughtworks.android.R
import com.thoughtworks.android.utils.Dependency
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class TweetsActivity : AppCompatActivity() {

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy { findViewById(R.id.swipeRefreshLayout) }
    private lateinit var tweetAdapter: TweetAdapter
    private lateinit var dependency: Dependency
    private val compositeDisposable = CompositeDisposable()
    private var shuffled = false

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

        swipeRefreshLayout.setOnRefreshListener {
            shuffled = true
            lifecycleScope.launch {
                tryFetchTweets()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun initData() {
        dependency = (application as PracticeApp).getDependency()
        val subscribe: Disposable = dependency.dataSource
            .observeTweets()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ tweets ->
                tweetAdapter.setData(if (shuffled) tweets.shuffled() else tweets)
                swipeRefreshLayout.isRefreshing = false
            }
            ) { throwable ->
                showErrorByToast(throwable)
                swipeRefreshLayout.isRefreshing = false
            }
        compositeDisposable.add(subscribe)
        lifecycleScope.launch {
            tryFetchTweets()
        }
    }

    private suspend fun tryFetchTweets() {
        try {
            dependency.dataSource.fetchTweets()
        } catch (e: Exception) {
            showErrorByToast(e)
        }
    }

    private fun showErrorByToast(e: Throwable) {
        Toast.makeText(
            this@TweetsActivity,
            e.message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}