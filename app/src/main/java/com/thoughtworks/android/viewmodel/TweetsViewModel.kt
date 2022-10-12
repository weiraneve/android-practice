package com.thoughtworks.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.android.common.MyResult
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

    private var _uiState = MutableStateFlow<MyResult<List<Tweet>>>(MyResult.Loading)
    val uiState = _uiState.asStateFlow()

    private var isNeedShuffled = false

    fun observeTweets() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchTweets().collect { res ->
                when (res) {
                    is MyResult.Loading -> _uiState.emit(MyResult.Loading)
                    is MyResult.Success -> {
                        if (isNeedShuffled) _uiState.emit(MyResult.Success(res.data.shuffled()))
                        else _uiState.emit(MyResult.Success(res.data))
                    }
                    is MyResult.Error -> {
                        _uiState.emit(MyResult.Error(res.exception))
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