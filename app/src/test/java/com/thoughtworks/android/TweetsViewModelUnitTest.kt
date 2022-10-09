package com.thoughtworks.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.Repository
import com.thoughtworks.android.data.source.local.LocalStorage
import com.thoughtworks.android.viewmodel.TweetsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
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

    private lateinit var repository: Repository
    private lateinit var localStorage: LocalStorage

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        repository = Mockito.mock(Repository::class.java)
        localStorage = Mockito.mock(LocalStorage::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun test_observe_tweets_successful() = runTest {
        val tweets: MutableList<Tweet> = mutableListOf()
        tweets.add(Tweet())
        tweets.add(Tweet())

        `when`(repository.fetchTweets()).thenReturn(tweets)

        val tweetsViewModel = TweetsViewModel(repository)
        tweetsViewModel.observeTweets()

        CoroutineScope(testDispatcher).launch {
            tweetsViewModel.tweetList.collect {
                Assert.assertEquals(2, it.size)
            }
        }

        CoroutineScope(testDispatcher).launch {
            tweetsViewModel.isNeedRefresh.collect {
                Assert.assertFalse(it)
            }
        }

        CoroutineScope(testDispatcher).launch {
            tweetsViewModel.errorMsg.collect {
                Assert.assertNull(it)
            }
        }

    }

    @Test
    fun test_observe_tweets_failed() = runTest {
        val tweets: MutableList<Tweet> = mutableListOf()
        tweets.add(Tweet())
        tweets.add(Tweet())

        `when`(repository.fetchTweets()).thenThrow(RuntimeException("error"))

        val tweetsViewModel = TweetsViewModel(repository)
        tweetsViewModel.observeTweets()

        CoroutineScope(testDispatcher).launch {
            tweetsViewModel.errorMsg.collect {
                Assert.assertNotNull(it)
            }
        }

        CoroutineScope(testDispatcher).launch {
            tweetsViewModel.errorMsg.collect {
                Assert.assertEquals("error", it)
            }
        }

    }

}