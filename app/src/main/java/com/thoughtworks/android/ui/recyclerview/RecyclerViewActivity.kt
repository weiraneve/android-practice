package com.thoughtworks.android.ui.recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.recycler_view_layout.*

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var tweetAdapter: TweetAdapter
    private lateinit var dependency: Dependency
    private val compositeDisposable = CompositeDisposable()
    private var shuffled = false

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
            shuffled = true
            dependency.dataSource.fetchTweets()
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
                Toast.makeText(
                    this@RecyclerViewActivity,
                    throwable.message,
                    Toast.LENGTH_SHORT
                ).show()
                swipeRefreshLayout.isRefreshing = false
            }
        compositeDisposable.add(subscribe)

        dependency.dataSource.fetchTweets()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}