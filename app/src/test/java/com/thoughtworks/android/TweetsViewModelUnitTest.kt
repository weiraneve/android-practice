//package com.thoughtworks.android
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.thoughtworks.android.common.Result
//import com.thoughtworks.android.data.model.Tweet
//import com.thoughtworks.android.data.source.Repository
//import com.thoughtworks.android.data.source.local.LocalStorage
//import com.thoughtworks.android.viewmodel.TweetsViewModel
//import kotlinx.coroutines.*
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.junit.*
//import org.junit.rules.TestRule
//import org.mockito.Mockito
//import org.mockito.Mockito.`when`
//import java.net.UnknownHostException
//
//
//@ExperimentalCoroutinesApi
//class TweetsViewModelUnitTest {
//
//    private lateinit var repository: Repository
//    private lateinit var localStorage: LocalStorage
//
//    @get:Rule
//    val rule: TestRule = InstantTaskExecutorRule()
//
//    @ExperimentalCoroutinesApi
//    private val testDispatcher = TestCoroutineDispatcher()
//
//    @Before
//    fun before() {
//        Dispatchers.setMain(testDispatcher)
//        repository = Mockito.mock(Repository::class.java)
//        localStorage = Mockito.mock(LocalStorage::class.java)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//        testDispatcher.cleanupTestCoroutines()
//    }
//
//    @Test
//    fun test_observe_tweets_successful() = runTest {
//        val tweets: MutableList<Tweet> = mutableListOf()
//        tweets.add(Tweet())
//        tweets.add(Tweet())
//
//        `when`(repository.fetchTweets()).thenReturn(
//            flow { Result.Success(tweets) }
//        )
//
//        val tweetsViewModel = TweetsViewModel(repository)
//        tweetsViewModel.observeTweets()
//
//        CoroutineScope(testDispatcher).launch {
//            tweetsViewModel.tweetList.collect {
//                Assert.assertEquals(2, it.size)
//            }
//        }
//
//        CoroutineScope(testDispatcher).launch {
//            tweetsViewModel.uiState.collect {
//                Assert.assertFalse(it.isNeedRefresh)
//            }
//        }
//
//        CoroutineScope(testDispatcher).launch {
//            tweetsViewModel.uiState.collect {
//                Assert.assertNull(it.errorMsg)
//            }
//        }
//
//    }
//
//    @Test
//    fun test_observe_tweets_failed_by_networkError() = runTest {
//        val tweets: MutableList<Tweet> = mutableListOf()
//        tweets.add(Tweet())
//        tweets.add(Tweet())
//
////        `when`(repository.fetchTweets()).thenThrow(UnknownHostException())
//        `when`(repository.fetchTweets()).thenThrow(RuntimeException())
//
//        val tweetsViewModel = TweetsViewModel(repository)
//        tweetsViewModel.observeTweets()
//
//        CoroutineScope(testDispatcher).launch {
//            tweetsViewModel.uiState.collect {
//                Assert.assertNotNull(it.errorMsg)
//            }
//        }
//
//        CoroutineScope(testDispatcher).launch {
//            tweetsViewModel.uiState.collect {
//                Assert.assertEquals(TweetsViewModel.NETWORK_ERROR, it.errorMsg)
//            }
//        }
//
//    }
//
//}