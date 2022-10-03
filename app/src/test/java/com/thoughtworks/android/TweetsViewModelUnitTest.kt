package com.thoughtworks.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thoughtworks.android.data.model.Tweet
import com.thoughtworks.android.data.source.DataSource
import com.thoughtworks.android.viewmodel.TweetsViewModel
import com.thoughtworks.android.utils.schedulers.SchedulerProvider
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.stub


@ExperimentalCoroutinesApi
class TweetsViewModelUnitTest {

    private lateinit var dataSource: DataSource

    private lateinit var schedulerProvider: SchedulerProvider

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)

        dataSource = Mockito.mock(DataSource::class.java)
        schedulerProvider = Mockito.mock(SchedulerProvider::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun test_fetch_tweets() = runBlocking {
        val tweets: MutableList<Tweet> = mutableListOf()
        tweets.add(Tweet())
        tweets.add(Tweet())

        val subject = PublishSubject.create<List<Tweet>>()
        `when`(dataSource.observeTweets()).thenReturn(subject.toFlowable(BackpressureStrategy.BUFFER))
        `when`(schedulerProvider.io()).thenReturn(Schedulers.trampoline())
        `when`(schedulerProvider.ui()).thenReturn(Schedulers.trampoline())

        dataSource.stub {
            onBlocking {
                fetchTweets()
            }.then {
                subject.onNext(tweets)
            }
        }

        val tweetsViewModel = TweetsViewModel(dataSource, schedulerProvider)
        tweetsViewModel.refreshTweets()

        Assert.assertEquals(2, tweetsViewModel.tweetList.value!!.size)
    }
}