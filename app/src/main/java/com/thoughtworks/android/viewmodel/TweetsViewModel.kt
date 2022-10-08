package com.thoughtworks.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.DataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TweetsViewModel @Inject constructor(private val dataSource: DataSource) : ViewModel() {

    private var _tweetList = MutableStateFlow<List<Tweet>>(mutableListOf())
    val tweetList = _tweetList.asStateFlow()

    private var _errorMsg = MutableStateFlow<Throwable?>(null)
    val errorMsg = _errorMsg.asStateFlow()

    private var _isNeedRefresh = MutableStateFlow(false)
    val isNeedRefresh = _isNeedRefresh.asStateFlow()

    private var isNeedShuffled = false

    init {
        observeTweets()
    }

    private fun observeTweets() {
        viewModelScope.launch(Dispatchers.IO) {
            val tweets = dataSource.observeTweets()
            if (isNeedShuffled) {
                _tweetList.emit(tweets.shuffled())
                isNeedShuffled = false
            }
            else _tweetList.emit(tweets)
        }
    }

    fun refreshTweets() {
        _isNeedRefresh.value = true
        fetchTweets()
        isNeedShuffled = true
        observeTweets()
        _isNeedRefresh.value = false
    }

    fun fetchTweets() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataSource.fetchTweets()
            } catch (t: Throwable) {
                _errorMsg.value = t
            }
        }
    }

}