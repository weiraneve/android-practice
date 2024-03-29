package com.thoughtworks.android.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.android.common.bean.Result
import com.thoughtworks.android.common.exception.NetworkReachableException
import com.thoughtworks.android.data.source.feed.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FeedUiState(
    val dataText: String? = ""
)

sealed class FeedUiAction {
    object FeedListAction : FeedUiAction()
}

@HiltViewModel
class FeedViewModel @Inject constructor(private val feedRepo: FeedRepository) : ViewModel() {

    private val _feedState = MutableStateFlow(FeedUiState())
    val feedState = _feedState.asStateFlow()

    private fun fetchFeed() {
        viewModelScope.launch {
            feedRepo.getFeedList().collect { res ->
                _feedState.update {
                    val result = when (res) {
                        is Result.Loading -> LOADING
                        is Result.Success -> res.data?.toString()
                        is Result.Error -> {
                            when (res.exception) {
                                is NetworkReachableException -> res.exception.message
                                else -> ERROR
                            }
                        }
                    }
                    it.copy(dataText = result)
                }
            }
        }
    }

    fun dispatchAction(action: FeedUiAction) {
        when (action) {
            FeedUiAction.FeedListAction -> fetchFeed()
        }
    }

    companion object {
        const val LOADING = "Loading"
        const val ERROR = "Error"
    }

}