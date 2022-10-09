package com.thoughtworks.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TweetsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _tweetList = MutableStateFlow<List<Tweet>>(mutableListOf())
    val tweetList = _tweetList.asStateFlow()

    private var _errorMsg = MutableStateFlow<Throwable?>(null)
    val errorMsg = _errorMsg.asStateFlow()

    private var _isNeedRefresh = MutableStateFlow(false)
    val isNeedRefresh = _isNeedRefresh.asStateFlow()

    private var isNeedShuffled = false

    fun observeTweets() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tweets = repository.fetchTweets()
                if (isNeedShuffled) {
                    _tweetList.emit(tweets.shuffled())
                    isNeedShuffled = false
                } else _tweetList.emit(tweets)
            } catch (e: Exception) {
                _errorMsg.value = e
            }
        }
    }

    fun refreshTweets() {
        _isNeedRefresh.value = true
        isNeedShuffled = true
        observeTweets()
        _isNeedRefresh.value = false
    }

}