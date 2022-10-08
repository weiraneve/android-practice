package com.thoughtworks.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.DataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TweetsViewModel @Inject constructor(private val dataSource: DataSource) : ViewModel() {

    private var _tweetList = MutableLiveData<List<Tweet>>(mutableListOf())
    val tweetList: LiveData<List<Tweet>> = _tweetList

    private var _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private var _isNeedRefresh = MutableLiveData(false)
    val isNeedRefresh: LiveData<Boolean> = _isNeedRefresh

    private var isNeedShuffled = false

    init {
        observeTweets()
    }

    private fun observeTweets() {
        viewModelScope.launch(Dispatchers.IO) {
            val tweets = dataSource.observeTweets()
            if (isNeedShuffled) {
                _tweetList.postValue(tweets.shuffled())
                isNeedShuffled = false
            }
            else _tweetList.postValue(tweets)
        }
    }

    fun refreshTweets() {
        _isNeedRefresh.postValue(true)
        fetchTweets()
        isNeedShuffled = true
        observeTweets()
        _isNeedRefresh.postValue(false)
    }

    fun fetchTweets() {
        viewModelScope.launch {
            try {
                dataSource.fetchTweets()
            } catch (t: Throwable) {
                _errorMsg.postValue(t.message)
            }
        }
    }

}