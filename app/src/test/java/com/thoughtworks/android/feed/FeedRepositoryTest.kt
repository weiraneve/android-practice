package com.thoughtworks.android.feed

import com.thoughtworks.android.common.Result
import com.google.common.truth.Truth.assertThat
import com.thoughtworks.android.data.source.feed.FeedApiDataSource
import com.thoughtworks.android.data.source.feed.FeedRepository
import com.thoughtworks.android.data.source.feed.model.FeedListEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FeedRepositoryTest {

    private val feedList = FeedListEntity(0, null)

    private val feedApiDataSource = mockk<FeedApiDataSource>()
    private val feedRepository = FeedRepository(feedApiDataSource)

    @Before
    fun setUp() {
        coEvery { feedApiDataSource.getFeedList() } returns flowOf(Result.Success(feedList))
    }

    @Test
    fun shouldCallDataSourceWhenFetchFeedListFromRepository() = runTest {
        // when
        val result = feedRepository.getFeedList().first()
        // then
        coVerify(exactly = 1) { feedApiDataSource.getFeedList() }
        assertThat((result as Result.Success).data).isEqualTo(feedList)
    }
}