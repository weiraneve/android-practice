package com.thoughtworks.android.ui.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.android.common.bean.MyRepoResult
import com.thoughtworks.android.common.bean.Result
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject


@HiltViewModel
class TweetsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _uiState = MutableStateFlow<Result<List<Tweet>>>(Result.Loading)
    val uiState = _uiState.asStateFlow()

    private var isNeedShuffled = false

    fun observeTweets() {
        viewModelScope.launch {
            repository.fetchTweets().collect { res ->
                when (res) {
                    is MyRepoResult.Success -> {
                        _uiState.emit(Result.Loading)
                        val tweets = res.data.filter { it.error == null && it.unknownError == null }
                        repository.saveTweets(tweets)
                        if (isNeedShuffled) {
                            _uiState.emit(Result.Success(tweets.shuffled()))
                            isNeedShuffled = false
                        } else _uiState.emit(Result.Success(tweets))
                    }
                    is MyRepoResult.Error -> {
                        if (res.exception is UnknownHostException) {
                            val tweets = repository.getTweets()
                            if (tweets.isEmpty()) {
                                _uiState.emit(Result.Error(res.exception))
                            } else MyRepoResult.Success(tweets)
                        } else _uiState.emit(Result.Error(res.exception))
                        _uiState.emit(Result.Error(res.exception))
                    }
                }
            }
        }
    }

    fun refreshTweets() {
        isNeedShuffled = true
        observeTweets()
    }

}