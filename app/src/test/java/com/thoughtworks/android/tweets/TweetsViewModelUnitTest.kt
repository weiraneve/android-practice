package com.thoughtworks.android.tweets

import com.thoughtworks.android.common.MyRepoResult
import com.thoughtworks.android.common.Result
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.Repository
import com.thoughtworks.android.ui.compose.TweetsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException


@ExperimentalCoroutinesApi
class TweetsViewModelUnitTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()

    private val repository = mockk<Repository>()
    private val remoteDataSource = mockk<Repository>()
    private val tweets: MutableList<Tweet> = mutableListOf()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        tweets.addAll(listOf(Tweet(), Tweet()))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_observe_tweets_successful() = runTest {
        // given
        coEvery { repository.fetchTweets() } returns flowOf(MyRepoResult.Success(tweets))
        coEvery { repository.saveTweets(tweets) } returns Unit
        val tweetsViewModel = TweetsViewModel(repository)
        // when
        tweetsViewModel.observeTweets()
        advanceUntilIdle()
        val result = tweetsViewModel.uiState.value
        // then
        Assert.assertNotNull(result)
        Assert.assertEquals(2, (result as Result.Success).data?.size)
    }

    @Test
    fun test_observe_tweets_failed_by_networkError() = runTest {
        // given
        coEvery { remoteDataSource.fetchTweets() } throws UnknownHostException(ERROR)
        coEvery { repository.fetchTweets() } returns flowOf(
            MyRepoResult.Error(
                UnknownHostException(
                    ERROR
                )
            )
        )
        coEvery { repository.getTweets() } returns emptyList()
        val tweetsViewModel = TweetsViewModel(repository)
        // when
        tweetsViewModel.observeTweets()
        advanceUntilIdle()
        val item = tweetsViewModel.uiState.value
        // then
        Assert.assertNotNull((item as Result.Error).exception.message)
        Assert.assertEquals(ERROR, item.exception.message)
    }

    companion object {
        const val ERROR = "network error"
    }

}