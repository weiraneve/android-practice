package com.thoughtworks.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thoughtworks.android.common.MyRepoResult
import com.thoughtworks.android.common.MyUIResult
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.Repository
import com.thoughtworks.android.viewmodel.TweetsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import java.net.UnknownHostException


@ExperimentalCoroutinesApi
class TweetsViewModelUnitTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    private val repository = mockk<Repository>()
    private val remoteDataSource = mockk<Repository>()
        private val tweets: MutableList<Tweet> = mutableListOf()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        tweets.add(Tweet())
        tweets.add(Tweet())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun test_observe_tweets_successful() = runTest {
        // given
        coEvery { repository.fetchTweets() } returns flowOf(MyRepoResult.Success(tweets))
        coEvery { repository.saveTweets(tweets) } returns Unit
        val tweetsViewModel = TweetsViewModel(repository)
        // when
        tweetsViewModel.observeTweets()
        val result = tweetsViewModel.uiState.value
        // then
        Assert.assertNotNull(result)
        Assert.assertEquals(2, (result as MyUIResult.Success).data.size)
    }

    @Test
    fun test_observe_tweets_failed_by_networkError() = runTest {
        // given
        coEvery { remoteDataSource.fetchTweets() } throws UnknownHostException(ERROR)
        coEvery { repository.fetchTweets() } returns flowOf(MyRepoResult.Error(UnknownHostException(ERROR)))
        coEvery { repository.getTweets() } returns emptyList()
        val tweetsViewModel = TweetsViewModel(repository)
        // when
        tweetsViewModel.observeTweets()
        val item = tweetsViewModel.uiState.value
        // then
        Assert.assertNotNull((item as MyUIResult.Error).exception.message)
        Assert.assertEquals(ERROR, item.exception.message)
    }

    companion object {
        const val ERROR = "network error"
    }

}