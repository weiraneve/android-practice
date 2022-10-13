//package com.thoughtworks.android
//
//import com.thoughtworks.android.data.source.Repository
//import com.thoughtworks.android.data.source.RepositoryImpl
//import com.thoughtworks.android.data.source.remote.RemoteDataSource
//import com.thoughtworks.android.data.source.remote.RemoteDataSourceImpl
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.flowOf
//import org.junit.Before
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.mockk
//import kotlinx.coroutines.test.runTest
//import org.junit.Test
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class RepositoryTest {
//
//    private val feedList = FeedListEntity(0, null)
//
//    private val feedApiDataSource = mockk<FeedApiDataSource>()
//    private val feedRepository = FeedRepository(feedApiDataSource)
//
//    private val remoteDataSource = mockk<RemoteDataSource>()
//    private val repository = RepositoryImpl()
//
//
//    @Before
//    fun setUp() {
//        w
//    }
//
//    @Test
//    fun `should call data source when fetch feed list from repository`() = runTest {
//        // when
//        val result = feedRepository.getFeedList().first()
//        // then
//        coVerify(exactly = 1) { feedApiDataSource.getFeedList() }
//        assertThat((result as Result.Success).data).isEqualTo(feedList)
//    }
//}