package com.thoughtworks.android.ui.recyclerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.DataSource
import com.thoughtworks.android.utils.schedulers.SchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.launch

class TweetsViewModel : ViewModel() {

    private lateinit var dataSource: DataSource
    private lateinit var schedulerProvider: SchedulerProvider
    private val compositeDisposable = CompositeDisposable()

    var tweetList: MutableLiveData<List<Tweet>> = MutableLiveData<List<Tweet>>(mutableListOf())

    fun setDependencies(dataSource: DataSource, schedulerProvider: SchedulerProvider) {
        this.dataSource = dataSource
        this.schedulerProvider = schedulerProvider

        val subscribe: Disposable = dataSource
            .observeTweets()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe { tweets -> tweetList.postValue(tweets) }
        compositeDisposable.add(subscribe)
    }

    fun fetchTweets(errorHandler: ((Throwable) -> Unit)? = null) {
        viewModelScope.launch {
            try {
                dataSource.fetchTweets()
            } catch (throwable: Throwable) {
                errorHandler?.invoke(throwable)
            }
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}