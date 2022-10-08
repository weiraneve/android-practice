package com.thoughtworks.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.DataSource
import com.thoughtworks.android.data.source.local.LocalStorage
import com.thoughtworks.android.data.source.remote.RemoteDataSource
import com.thoughtworks.android.viewmodel.TweetsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.Mockito.`when`


@ExperimentalCoroutinesApi
class TweetsViewModelUnitTest {

    private lateinit var dataSource: DataSource
    private lateinit var localStorage: LocalStorage
    private lateinit var remoteDataSource: RemoteDataSource

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        dataSource = Mockito.mock(DataSource::class.java)
        localStorage = Mockito.mock(LocalStorage::class.java)
        remoteDataSource = Mockito.mock(RemoteDataSource::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun test_fetch_tweets_successful() = runTest {
        val tweets: MutableList<Tweet> = mutableListOf()
        tweets.add(Tweet())
        tweets.add(Tweet())

        `when`(dataSource.observeTweets()).thenReturn(tweets)
        `when`(remoteDataSource.fetchTweets()).thenReturn(tweets)

        val tweetsViewModel = TweetsViewModel(dataSource)
        tweetsViewModel.fetchTweets()

        Assert.assertEquals(2, tweetsViewModel.tweetList.value!!.size)
        Assert.assertEquals(false, tweetsViewModel.isNeedRefresh.value)
        Assert.assertNull(tweetsViewModel.errorMsg.value)
    }

    @Test
    fun test_fetch_tweets_failed() = runTest {
        val tweets: MutableList<Tweet> = mutableListOf()
        tweets.add(Tweet())
        tweets.add(Tweet())

        `when`(dataSource.fetchTweets()).thenThrow(RuntimeException("error"))

        val tweetsViewModel = TweetsViewModel(dataSource)
        tweetsViewModel.fetchTweets()

        Assert.assertNotNull(tweetsViewModel.errorMsg.value)
        Assert.assertEquals("error", tweetsViewModel.errorMsg.value)
    }

}